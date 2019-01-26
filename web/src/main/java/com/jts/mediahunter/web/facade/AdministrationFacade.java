package com.jts.mediahunter.web.facade;

import com.jts.mediahunter.web.dto.ChannelInfoDTO;
import com.jts.mediahunter.web.dto.FindChannelDTO;
import com.jts.mediahunter.web.dto.FindRecordDTO;
import com.jts.mediahunter.web.dto.RecordInfoDTO;
import java.util.List;

/**
 *
 * @author Tony
 */
public interface AdministrationFacade {

    /**
     * Gets all channels from Media Content Providers (MCP) and DB and combines
     * them. Channels with internalId != null are already in database.
     *
     * @param externalId ID provided by MCP
     * @return
     */
    public List<FindChannelDTO> getChannelsByExternalId(String externalId);

    /**
     * Puts new channel to database.
     *
     * @param externalId ID provided by Media Content Provider (MCP)
     * @param mcpName name of MCP - used for decision which plugin to use
     * @param trusted wether channel records should go straight to accepted stage
     * @return ID created by DB (internalId)
     */
    public String putChannelToDB(String externalId, String mcpName, boolean trusted);

    /**
     * Finds and return all information about channel with given internalId
     * (from DB)
     *
     * @param internalId
     * @return
     */
    public ChannelInfoDTO getChannelInfo(String internalId);

    /**
     * Updates channel with given internalId
     *
     * @param internalId
     */
    public void updateChannel(String internalId);

    /**
     * Deletes channel from database.
     *
     * @param internalId unique ID in DB
     * @param deleteAllChannelRecords indicates wether to delete all records
     * uploaded by this channel or to keep them.
     */
    public void deleteChannel(String internalId, boolean deleteAllChannelRecords);

    public List<FindRecordDTO> getRecordsByExternalId(String externalId);
    
    public String putRecordToDB(String externalId, String mcpName);
    
    public RecordInfoDTO getRecordInfo(String internalId);
    
    public void updateRecord(String internalId);
    
    public List<FindRecordDTO> getWaitingRecords();
    
    public void acceptRecord(String internalId);
    
    public void rejectRecord(String internalId);
    
    public void addAllNewMedia();
    
}
