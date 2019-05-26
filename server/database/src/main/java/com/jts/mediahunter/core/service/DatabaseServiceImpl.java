package com.jts.mediahunter.core.service;

import com.jts.mediahunter.core.dao.ChannelDAO;
import com.jts.mediahunter.core.dao.MultimediumDAO;
import com.jts.mediahunter.domain.RecordStage;
import com.jts.mediahunter.domain.entities.Channel;
import com.jts.mediahunter.domain.entities.Multimedium;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

/**
 * @author Tony
 */
@Component
@Slf4j
public class DatabaseServiceImpl implements DatabaseService {

    @Autowired
    private MultimediumDAO multimediumDAO;

    @Autowired
    private ChannelDAO channelDAO;

    @Override
    public List<Channel> getChannelsByExternalId(String externalId) {
        return channelDAO.findByExternalId(externalId);
    }

    @Override
    public Channel putChannelToDB(Channel channel) {
        Channel newChannel = channelDAO.insert(channel);
        log.info("Inserted: {}", newChannel.toString());
        return newChannel;
    }

    @Override
    public Channel getChannelById(String internalId) {
        return channelDAO.findById(internalId).orElse(null);
    }

    @Override
    public void updateChannel(Channel channel) {
        channelDAO.save(channel);
    }

    @Override
    public void deleteChannel(String internalID, boolean deleteAllChannelMultimedia) {
        Channel channel = getChannelById(internalID);
        if (channel == null) {
            log.error("Channel with ID: " + internalID + " not found");
            return;
        }
        if (deleteAllChannelMultimedia) {
            List<Multimedium> multimedia = multimediumDAO.findByUploader(channel.getExternalId(), channel.getMcpName());
            multimediumDAO.deleteAll(multimedia);
        }
        channelDAO.delete(channel);
    }

    @Override
    public List<Multimedium> getMultimediaByExternalId(String externalId) {
        return multimediumDAO.findByExternalId(externalId);
    }

    @Override
    public String putMultimediumToDB(Multimedium multimedium) {
        multimedium.setStage(RecordStage.WAITING);
        Multimedium inserted = multimediumDAO.insert(multimedium);
        log.info("Inserted: {}", inserted.toString());
        return inserted.getId();
    }

    @Override
    public Multimedium getMultimediumById(String internalId) {
        return multimediumDAO.findById(internalId).orElse(null);
    }

    @Override
    public void updateMultimedium(Multimedium multimedium) {
        multimediumDAO.save(multimedium);
    }

    @Override
    public void acceptMultimedium(Multimedium multimedium) {
        if (multimedium == null || multimedium.getStage() == RecordStage.ACCEPTED) {
            return;
        }
        String mcpName = multimedium.getMcpName();
        Channel channel = getChannelsByExternalId(multimedium.getUploaderExternalId())
                .stream()
                .filter(ch -> ch.getMcpName().equals(mcpName))
                .findAny()
                .orElse(null);
        multimedium.setStage(RecordStage.ACCEPTED);
        multimedium = multimediumDAO.save(multimedium);
        if (channel != null) {
            channel.registerNewAcceptedMultimedium(multimedium);
            updateChannel(channel);
        }
        log.info("Multimedium with ID: " + multimedium.getId() + " was " + multimedium.getStage().toString());
    }

    @Override
    public List<Multimedium> getWaitingMultimedia() {
        return multimediumDAO.findWaitingMultimedia();
    }

    @Override
    public List<Channel> getAllChannels() {
        return channelDAO.findAll();
    }

    @Override
    public List<Multimedium> getMultimediaPage(int page) {
        return multimediumDAO.findAcceptedMultimedia(PageRequest.of(page, 10,
                new Sort(Sort.Direction.DESC, "uploadTime"))).getContent();
    }

    @Override
    public List<Multimedium> getMultimediaByUploaderExtednalId(String uploaderExternalId) {
        return multimediumDAO.findByUploaderExternalId(uploaderExternalId);
    }

    @Override
    public List<Channel> getTrustedChannels() {
        return channelDAO.findByTrustedIsTrue();
    }

    @Override
    public void rejectMultimedium(Multimedium multimedium) {
        if (multimedium == null || multimedium.getStage() == RecordStage.REJECTED) {
            return;
        }
        multimedium.setStage(RecordStage.REJECTED);
        multimedium = multimediumDAO.save(multimedium);
        String mcpName = multimedium.getMcpName();
        Channel channel = getChannelsByExternalId(multimedium.getUploaderExternalId())
                .stream()
                .filter(ch -> ch.getMcpName().equals(mcpName))
                .findAny()
                .orElse(null);
        if (channel != null) {
            channel.acceptedMultimediumRejected();
            updateChannel(channel);
        }
        log.info("Multimedium with ID: " + multimedium.getId() + " was " + multimedium.getStage().toString());
    }

}
