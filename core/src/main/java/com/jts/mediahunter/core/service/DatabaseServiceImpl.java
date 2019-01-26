package com.jts.mediahunter.core.service;

import com.jts.mediahunter.core.dao.ChannelDAO;
import com.jts.mediahunter.core.dao.RecordDAO;
import com.jts.mediahunter.domain.RecordStage;
import com.jts.mediahunter.domain.entities.Channel;
import com.jts.mediahunter.domain.entities.Record;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Tony
 */
@Component
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
        return channelDAO.insert(channel).getId();
    }

    @Override
    public Channel getChannelById(String internalId) {
        return channelDAO.findById(internalId).orElse(null);
    }

    @Override
    public void updateChannel(Channel channel) {
        channelDAO.save(channel);
    }

    @Override
    public void deleteChannel(String internalID, boolean deleteAllChannelRecords) {
        Channel channel = channelDAO.findById(internalID).orElse(null);
        if (channel != null) {
            //TODO check if channel and all records had been deleted
            // if not, return to state before this method!
            channelDAO.delete(channel);
            if (deleteAllChannelRecords) {
                List<Record> records = recordDAO.findByUploader(channel.getExternalId(), channel.getNameOfMcp());
                for (Record record : records) {
                    recordDAO.delete(record);
                }
            }
        }
    }

    @Override
    public List<Record> getRecordsByExternalId(String externalId) {
        return recordDAO.findByExternalId(externalId);
    }

    @Override
    public String putRecordToDB(Record record) {
        record.setStage(RecordStage.WAITING);
        record = recordDAO.insert(record);
        return record.getId();
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
        recordDAO.save(record);
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
    public void rejectRecord(Record record) {
        //TODO record -> internalID, service can do it all!
        record.setStage(RecordStage.REJECTED);
        recordDAO.save(record);
    }

}
