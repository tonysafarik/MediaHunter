package com.jts.mediahunter.web.facade;

import com.jts.mediahunter.domain.dto.*;

import java.util.List;

/**
 *
 * @author Tony
 */
public interface AdministrationFacade {

    /**
     * Gets all channels from Media Content Providers (MCP) and DB and combines
     * them. Channels with id != null are already in database.
     *
     * @param externalId ID provided by MCP
     * @return
     */
    public List<ChannelPreviewDTO> getChannelsByExternalId(String externalId);

    /**
     * Puts new channel to database.
     *
     * @param externalId ID provided by Media Content Provider (MCP)
     * @param mcpName name of MCP - used for decision which plugin to use
     * @param trusted wether channel multimedia should go straight to accepted stage
     * @return ID created by DB (id)
     */
    public ChannelInfoDTO putChannelToDB(String externalId, String mcpName, boolean trusted);

    /**
     * Finds and return all information about channel with given id
     * (from DB)
     *
     * @param internalId
     * @return
     */
    public ChannelInfoDTO getChannelInfo(String internalId);

    /**
     * Updates channel with given id
     *
     * @param internalId
     */
    public void updateChannel(String internalId);

    /**
     * Deletes channel from database.
     *
     * @param internalId unique ID in DB
     * @param deleteAllChannelMultimedia indicates whether to delete all multimedia
     * uploaded by this channel or to keep them.
     */
    public void deleteChannel(String internalId, boolean deleteAllChannelMultimedia);

    public List<MultimediumPreviewDTO> getMultimediaByExternalId(String externalId);
    
    public String putMultimediumToDB(String externalId, String mcpName);
    
    public MultimediumInfoDTO getMultimediumInfo(String internalId);
    
    public void updateMultimedium(String internalId);
    
    public List<MultimediumPreviewDTO> getWaitingMultimedia();
    
    public void acceptMultimedium(String internalId);
    
    public void rejectMultimedium(String internalId);
    
    public void addAllNewMedia();

    public List<PublicMultimediumDTO> getMultimediaPage(int page);

    public List<PublicChannelDTO> getTrustedChannels();
}
