package com.jts.mediahunter.core.service;

import com.jts.mediahunter.domain.entities.Channel;
import com.jts.mediahunter.domain.entities.Record;
import com.jts.mediahunter.plugins.MediaContentProviderPlugin;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Tony
 */
@Component
public class PluginServiceImpl implements PluginService {

    @Autowired
    private List<MediaContentProviderPlugin> plugins;
    
    @Override
    public List<Channel> getChannelsByExternalId(String externalId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Channel getChannelByExternalIdFromMCP(String externalId, String nameOfMCP) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Record> getRecordsByExternalId(String externalId) {
        List<Record> records = new ArrayList<>();
        for (MediaContentProviderPlugin plugin : plugins) {
            records.add(plugin.getRecordByExternalId(externalId));
        }
        return records;
    }

    @Override
    public Record getRecordByExternalIdFromMCP(String externalId, String nameOfMCP) {
        for (MediaContentProviderPlugin plugin : plugins) {
            if (plugin.getMcpName().equals(nameOfMCP)) {
                return plugin.getRecordByExternalId(externalId);
            }
        }
        return null;
    }
    
}
