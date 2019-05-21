package com.jts.mediahunter.core.service;

import com.jts.mediahunter.core.dao.ChannelDAO;
import com.jts.mediahunter.core.dao.RecordDAO;
import com.jts.mediahunter.domain.RecordStage;
import com.jts.mediahunter.domain.dto.ChannelInfoDTO;
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
    public Channel putChannelToDB(Channel channel) {
        Channel newChannel = channelDAO.insert(channel);
        log.info("Inserted: {}", newChannel.toString());
        return newChannel;
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
        Channel channel = getChannelById(internalID);
        if (channel == null) {
            log.error("Channel with ID: " + internalID + " not found");
            return;
        }
        if (deleteAllChannelRecords) {
            List<Record> records = recordDAO.findByUploader(channel.getExternalId(), channel.getMcpName());
            recordDAO.deleteAll(records);
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
        log.info("Inserted: {}", inserted.toString());
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
        if (record == null || record.getStage() == RecordStage.ACCEPTED) {
            return;
        }
        String mcpName = record.getMcpName();
        Channel channel = getChannelsByExternalId(record.getUploaderExternalId())
                .stream()
                .filter(ch -> ch.getMcpName().equals(mcpName))
                .findAny()
                .orElse(null);
        record.setStage(RecordStage.ACCEPTED);
        record = recordDAO.save(record);
        if (channel != null) {
            channel.registerNewAcceptedRecord(record);
            updateChannel(channel);
        }
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
    public List<Record> getMultimediaByUploaderExtednalId(String uploaderExternalId) {
        return recordDAO.findByUploaderExternalId(uploaderExternalId);
    }

    @Override
    public void rejectRecord(Record record) {
        if (record == null || record.getStage() == RecordStage.REJECTED) {
            return;
        }
        record.setStage(RecordStage.REJECTED);
        record = recordDAO.save(record);
        String mcpName = record.getMcpName();
        Channel channel = getChannelsByExternalId(record.getUploaderExternalId())
                .stream()
                .filter(ch -> ch.getMcpName().equals(mcpName))
                .findAny()
                .orElse(null);
        if (channel != null) {
            channel.acceptedRecordRejected();
            updateChannel(channel);
        }
        log.info("Record with ID: " + record.getId() + " was " + record.getStage().toString());
    }

}
