package com.jts.mediahunter.core.service;

import com.jts.mediahunter.domain.dto.ChannelInfoDTO;
import com.jts.mediahunter.domain.entities.Channel;
import com.jts.mediahunter.domain.entities.Record;

import java.util.List;

/**
 * @author Tony
 */
public interface DatabaseService {

    public List<Channel> getChannelsByExternalId(String externalId);

    public Channel getChannelById(String internalId);

    public Channel putChannelToDB(Channel channel);

    public void updateChannel(Channel channel);

    public void deleteChannel(String internalID, boolean deleteAllChannelRecords);

    public List<Record> getRecordsByExternalId(String externalId);

    public String putRecordToDB(Record record);

    public Record getRecordById(String internalId);

    public void updateRecord(Record record);

    public void acceptRecord(Record record);

    public void rejectRecord(Record record);

    public List<Record> getWaitingRecords();

    public List<Channel> getAllChannels();

    public List<Record> getRecordPage(int page);

    public List<Record> getMultimediaByUploaderExtednalId(String uploaderExternalId);

    public List<Channel> getTrustedChannels();
}
