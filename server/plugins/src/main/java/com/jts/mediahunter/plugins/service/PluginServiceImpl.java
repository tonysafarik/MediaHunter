package com.jts.mediahunter.plugins.service;

import com.jts.mediahunter.domain.entities.Channel;
import com.jts.mediahunter.domain.entities.Multimedium;
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
    public List<Multimedium> getMultimediaByExternalId(String externalId) {
        List<Multimedium> multimedia = new ArrayList<>();
        for (Map.Entry<String, MediaContentProviderPlugin> entry : this.plugins.entrySet()) {
            Multimedium multimedium = entry.getValue().getMultimediumByExternalId(externalId);
            if (multimedium != null) {
                multimedia.add(multimedium);
            }
        }
        return multimedia;
    }

    @Override
    public Multimedium getMultimediumByExternalId(String externalId, String nameOfMCP) {
        return this.plugins.get(nameOfMCP).getMultimediumByExternalId(externalId);
    }

    @Override
    public List<Multimedium> getMultimediaByUploaderExternalId(String externalId, String nameOfMCP) {
        return this.plugins.get(nameOfMCP).getAllChannelMultimedia(externalId);
    }

    @Override
    public List<Multimedium> getMultimediaByUploaderExternalId(String uploaderExternalId, String nameOfMCP, LocalDateTime from) {
        return this.plugins.get(nameOfMCP).getNewMultimedia(uploaderExternalId, from);
    }

}
