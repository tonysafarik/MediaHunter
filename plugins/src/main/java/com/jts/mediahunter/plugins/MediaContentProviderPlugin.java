package com.jts.mediahunter.plugins;

import com.jts.mediahunter.domain.entities.Channel;
import com.jts.mediahunter.domain.entities.Record;
import java.util.List;

/**
 *
 * @author Tony
 */
public interface MediaContentProviderPlugin {

    /**
     * Finds all channels with given external channel ID. Ideally, the list
     * should contain only one Channel entity, but list is used in case two
     * media content providers (MCPs) use same ID. Never set ID (internal) of
     * the Channel entities, it will be set during DB insertion.
     *
     * @param channelId ID of Channel given by service
     * @return List of Channel entities
     */
    public List<Channel> getChannelByExternalId(String channelId);

    /**
     * Finds all records with given external record ID. Ideally, the list should
     * contain only one record entity, but list is used in case two media
     * content providers (MCPs) use same ID. Never set ID (internal) of the
     * Record entities, it will be set during DB insertion.
     *
     * @param recordId ID of Record given by service
     * @return List of Record entities
     */
    public List<Record> getRecordByExternalId(String recordId);

    /**
     *  
     * @return String with name of Media Content Provider (MCP) 
     */
    public String getMcpName();
    
}
