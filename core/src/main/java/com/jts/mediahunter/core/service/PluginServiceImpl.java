package com.jts.mediahunter.core.service;

import com.jts.mediahunter.domain.entities.Channel;
import com.jts.mediahunter.domain.entities.Record;
import com.jts.mediahunter.plugins.MediaContentProviderPlugin;

import java.time.LocalDateTime;
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
        List<Channel> channels = new ArrayList<>();
        for (MediaContentProviderPlugin plugin : plugins) {
            channels.add(plugin.getChannelByExternalId(externalId));
        }
        return channels;
    }

    @Override
    public Channel getChannelByExternalId(String externalId, String nameOfMCP) {
        for (MediaContentProviderPlugin plugin : plugins) {
            if (plugin.getMcpName().equals(nameOfMCP)) {
                return plugin.getChannelByExternalId(externalId);
            }
        }
        return null;
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
    public Record getRecordByExternalId(String externalId, String nameOfMCP) {
        for (MediaContentProviderPlugin plugin : plugins) {
            if (plugin.getMcpName().equals(nameOfMCP)) {
                return plugin.getRecordByExternalId(externalId);
            }
        }
        return null;
    }

    @Override
    public List<Record> getRecordsByUploaderExternalId(String externalId, String nameOfMCP) {
        for (MediaContentProviderPlugin plugin : plugins) {
            if (plugin.getMcpName().equals(nameOfMCP)) {
                return plugin.getAllChannelRecords(externalId);
            }
        }
        return new ArrayList<>();
    }

    @Override
    public List<Record> getRecordsByUploaderExternalId(String uploaderExternalId, String nameOfMCP, LocalDateTime from) {
        for (MediaContentProviderPlugin plugin : plugins) {
            if (plugin.getMcpName().equals(nameOfMCP)) {
                return plugin.getNewRecords(uploaderExternalId, from);
            }
        }
        return new ArrayList<>();
    }

}
