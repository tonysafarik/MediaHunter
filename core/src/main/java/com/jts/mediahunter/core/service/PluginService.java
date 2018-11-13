package com.jts.mediahunter.core.service;

import com.jts.mediahunter.domain.entities.Channel;
import com.jts.mediahunter.domain.entities.Record;
import com.jts.mediahunter.plugins.MediaContentProviderPlugin;
import java.util.List;

/**
 *
 * @author Tony
 */
public interface PluginService {

    public List<Channel> getChannelsByExternalId(String externalId);
    
    public Channel getChannelByExternalIdFromMCP(String externalId, String nameOfMCP);
    
    public List<Record> getRecordsByExternalId(String externalId);
    
    public Record getRecordByExternalIdFromMCP(String externalId, String nameOfMCP);
    
}
