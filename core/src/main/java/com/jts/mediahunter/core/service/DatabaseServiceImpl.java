package com.jts.mediahunter.core.service;

import com.jts.mediahunter.core.dao.ChannelDAO;
import com.jts.mediahunter.core.dao.RecordDAO;
import com.jts.mediahunter.domain.RecordStage;
import com.jts.mediahunter.domain.entities.Channel;
import com.jts.mediahunter.domain.entities.Record;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

/**
 * @author Tony
 */
@Component
@Slf4j
public class DatabaseServiceImpl implements DatabaseService {

    @Autowired
    private RecordDAO recordDAO;

    @Autowired
    private ChannelDAO channelDAO;

    @Override
    public List<Channel> getChannelsByExternalId(String externalId) {
        return channelDAO.findByExternalId(externalId);
    }

    @Override
    public String putChannelToDB(Channel channel) {
        Channel inserted = channelDAO.insert(channel);

        if (inserted.getId() != null) {
            log.info("Channel: " + channel.toString() + " inserted successfully.");
        } else {
            log.error("Channel: " + channel.toString() + " NOT inserted successfully");
        }

        return inserted.getId();
    }

    @Override
    public Channel getChannelById(String internalId) {
        return channelDAO.findById(internalId).orElse(null);
    }

    @Override
    public void updateChannel(Channel channel) {
        channelDAO.save(channel);
    }

    private void checkAllRecordsDeleted(List<Record> records){
        for (Record record :
                records) {
            log.error("Record with external ID: " + record.getExternalId() + " from " + record.getMcpName() + " was NOT deleted, but should be");
        }
    }

    @Override
    public void deleteChannel(String internalID, boolean deleteAllChannelRecords) {
        Channel channel = channelDAO.findById(internalID).orElse(null);
        if (channel == null) {
            log.error("Channel with ID: " + internalID + " not found");
            return;
        }
        if (deleteAllChannelRecords) {
            List<Record> records = recordDAO.findByUploader(channel.getExternalId(), channel.getMcpName());
            recordDAO.deleteAll(records);
            checkAllRecordsDeleted(recordDAO.findByUploader(channel.getExternalId(), channel.getMcpName()));
        }
        channelDAO.delete(channel);
    }

    @Override
    public List<Record> getRecordsByExternalId(String externalId) {
        return recordDAO.findByExternalId(externalId);
    }

    @Override
    public String putRecordToDB(Record record) {
        record.setStage(RecordStage.WAITING);
        Record inserted = recordDAO.insert(record);

        if (inserted.getId() != null) {
            log.info("Record: " + record.toString() + " inserted successfully.");
        } else {
            log.error("Record: " + record.toString() + " was NOT inserted successfully");
        }

        return inserted.getId();
    }

    @Override
    public Record getRecordById(String internalId) {
        return recordDAO.findById(internalId).orElse(null);
    }

    @Override
    public void updateRecord(Record record) {
        recordDAO.save(record);
    }

    @Override
    public void acceptRecord(Record record) {
        //TODO record -> internalID, service can do it all!
        getChannelsByExternalId(record.getUploaderExternalId());
        record.setStage(RecordStage.ACCEPTED);
        record = recordDAO.save(record);
        log.info("Record with ID: " + record.getId() + " was " + record.getStage().toString());
    }

    @Override
    public List<Record> getWaitingRecords() {
        return recordDAO.findWaitingRecords();
    }

    @Override
    public List<Channel> getAllChannels() {
        return channelDAO.findAll();
    }

    @Override
    public List<Record> getRecordPage(int page) {
        return recordDAO.findAcceptedRecords(PageRequest.of(page, 10,
                new Sort(Sort.Direction.DESC, "uploadTime"))).getContent();
    }

    @Override
    public void rejectRecord(Record record) {
        //TODO record -> internalID, service can do it all!
        record.setStage(RecordStage.REJECTED);
        record = recordDAO.save(record);
        log.info("Record with ID: " + record.getId() + " was " + record.getStage().toString());
    }

}
