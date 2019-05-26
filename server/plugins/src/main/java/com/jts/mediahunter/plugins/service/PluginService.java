package com.jts.mediahunter.plugins.service;

import com.jts.mediahunter.domain.entities.Channel;
import com.jts.mediahunter.domain.entities.Multimedium;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Tony
 */
public interface PluginService {

    public List<Channel> getChannelsByExternalId(String externalId);
    
    public Channel getChannelByExternalId(String externalId, String nameOfMCP);
    
    public List<Multimedium> getMultimediaByExternalId(String externalId);
    
    public Multimedium getMultimediumByExternalId(String externalId, String nameOfMCP);
    
    public List<Multimedium> getMultimediaByUploaderExternalId(String uploaderExternalId, String nameOfMCP);

    public List<Multimedium> getMultimediaByUploaderExternalId(String uploaderExternalId, String nameOfMCP, LocalDateTime from);

}
