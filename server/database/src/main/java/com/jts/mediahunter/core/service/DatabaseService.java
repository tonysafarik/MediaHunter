package com.jts.mediahunter.core.service;

import com.jts.mediahunter.domain.entities.Channel;
import com.jts.mediahunter.domain.entities.Multimedium;

import java.util.List;

/**
 * @author Tony
 */
public interface DatabaseService {

    public List<Channel> getChannelsByExternalId(String externalId);

    public Channel getChannelById(String internalId);

    public Channel putChannelToDB(Channel channel);

    public void updateChannel(Channel channel);

    public void deleteChannel(String internalID, boolean deleteAllChannelMultimedia);

    public List<Multimedium> getMultimediaByExternalId(String externalId);

    public String putMultimediumToDB(Multimedium multimedium);

    public Multimedium getMultimediumById(String internalId);

    public void updateMultimedium(Multimedium multimedium);

    public void acceptMultimedium(Multimedium multimedium);

    public void rejectMultimedium(Multimedium multimedium);

    public List<Multimedium> getWaitingMultimedia();

    public List<Channel> getAllChannels();

    public List<Multimedium> getMultimediaPage(int page);

    public List<Multimedium> getMultimediaByUploaderExtednalId(String uploaderExternalId);

    public List<Channel> getTrustedChannels();
}
