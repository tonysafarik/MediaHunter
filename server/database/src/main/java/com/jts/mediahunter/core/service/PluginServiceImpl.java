package com.jts.mediahunter.core.service;

import com.jts.mediahunter.domain.entities.Channel;
import com.jts.mediahunter.domain.entities.Record;
import com.jts.mediahunter.plugins.MediaContentProviderPlugin;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Tony
 */
@Component
@Slf4j
public class PluginServiceImpl implements PluginService {

    private Map<String, MediaContentProviderPlugin> plugins;

    @Autowired
    public PluginServiceImpl(List<MediaContentProviderPlugin> plugins) {
        this.plugins = new HashMap<>();
        for (MediaContentProviderPlugin plugin: plugins) {
            this.plugins.put(plugin.getMcpName(), plugin);
        }
    }

    @Override
    public List<Channel> getChannelsByExternalId(String externalId) {
        List<Channel> channels = new ArrayList<>();
        for (Map.Entry<String, MediaContentProviderPlugin> entry : this.plugins.entrySet()) {
            Channel channel = entry.getValue().getChannelByExternalId(externalId);
            if (channel != null) {
                channels.add(channel);
            }
        }
        log.info(channels.toString());
        return channels;
    }

    @Override
    public Channel getChannelByExternalId(String externalId, String nameOfMCP) {
        return this.plugins.get(nameOfMCP).getChannelByExternalId(externalId);
    }

    @Override
    public List<Record> getRecordsByExternalId(String externalId) {
        List<Record> multimedia = new ArrayList<>();
        for (Map.Entry<String, MediaContentProviderPlugin> entry : this.plugins.entrySet()) {
            Record record = entry.getValue().getRecordByExternalId(externalId);
            if (record != null) {
                multimedia.add(record);
            }
        }
        return multimedia;
    }

    @Override
    public Record getRecordByExternalId(String externalId, String nameOfMCP) {
        return this.plugins.get(nameOfMCP).getRecordByExternalId(externalId);
    }

    @Override
    public List<Record> getRecordsByUploaderExternalId(String externalId, String nameOfMCP) {
        return this.plugins.get(nameOfMCP).getAllChannelRecords(externalId);
    }

    @Override
    public List<Record> getRecordsByUploaderExternalId(String uploaderExternalId, String nameOfMCP, LocalDateTime from) {
        return this.plugins.get(nameOfMCP).getNewRecords(uploaderExternalId, from);
    }

}
