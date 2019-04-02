package com.jts.mediahunter.core.service;

import com.jts.mediahunter.domain.entities.Channel;
import com.jts.mediahunter.domain.entities.Record;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Tony
 */
public interface PluginService {

    public List<Channel> getChannelsByExternalId(String externalId);
    
    public Channel getChannelByExternalId(String externalId, String nameOfMCP);
    
    public List<Record> getRecordsByExternalId(String externalId);
    
    public Record getRecordByExternalId(String externalId, String nameOfMCP);
    
    public List<Record> getRecordsByUploaderExternalId(String uploaderExternalId, String nameOfMCP);

    public List<Record> getRecordsByUploaderExternalId(String uploaderExternalId, String nameOfMCP, LocalDateTime from);

}
