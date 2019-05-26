package com.jts.mediahunter.plugins;

import com.jts.mediahunter.domain.entities.Channel;
import com.jts.mediahunter.domain.entities.Multimedium;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Tony
 */
public interface MediaContentProviderPlugin {

    /**
     * Finds channel with given external channel ID. Never set ID (internal) of
     * the Channel entity, it will be set during DB insertion.
     *
     * @param channelId ID of Channel given by MCP
     * @return Channel entity
     */
    public Channel getChannelByExternalId(String channelId);

    /**
     * Finds multimedia with given external multimedium ID. Never set ID (internal) of the
     * Multimedium entity, it will be set during DB insertion.
     *
     * @param multimediumExternalID ID of Multimedium given by MCP
     * @return Multimedium entity
     */
    public Multimedium getMultimediumByExternalId(String multimediumExternalID);

    /**
     *
     * @return String with name of Media Content Provider (MCP)
     */
    public String getMcpName();

    /**
     * Finds all multimedia uploaded by this channel until the time this method is
     * called.
     *
     * @param channelId external id of the channel
     * @return List of Multimedium entities
     */
    public List<Multimedium> getAllChannelMultimedia(String channelId);

    /**
     * Finds all multimedia from "oldestMultimedium" to the newest uploaded by channel with given external ID
     * @param channelId external ID of channel
     * @param oldestMultimedium date and time of newest multimedium (of channel) already in database
     * @return List of multimedia
     */
    public List<Multimedium> getNewMultimedia(String channelId, LocalDateTime oldestMultimedium);
    
}
