package com.jts.mediahunter.plugins;

import com.jts.mediahunter.domain.entities.Channel;
import com.jts.mediahunter.domain.entities.Record;
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
     * Finds record with given external record ID. Never set ID (internal) of the
     * Record entity, it will be set during DB insertion.
     *
     * @param recordId ID of Record given by MCP
     * @return Record entity
     */
    public Record getRecordByExternalId(String recordId);

    /**
     *
     * @return String with name of Media Content Provider (MCP)
     */
    public String getMcpName();

    /**
     * Finds all records uploaded by this channel until the time this method is
     * called.
     *
     * @param channelId external id of the channel
     * @return List of Record entities
     */
    public List<Record> getAllChannelRecords(String channelId);

    /**
     * Finds all records from "oldestRecord" to the newest uploaded by channel with given external ID
     * @param channelId
     * @param oldestRecord
     * @return 
     */
    public List<Record> getNewRecords(String channelId, LocalDateTime oldestRecord);
    
}
