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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteChannel(String internalID, boolean deleteAllChannelRecords) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        record.setStage(RecordStage.ACCEPTED);
        recordDAO.save(record);
    }

}
