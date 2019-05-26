package com.jts.mediahunter.web.facade;

import com.jts.mediahunter.core.service.DatabaseService;
import com.jts.mediahunter.plugins.service.PluginService;
import com.jts.mediahunter.domain.RecordStage;
import com.jts.mediahunter.domain.entities.Channel;
import com.jts.mediahunter.domain.entities.Multimedium;
import com.jts.mediahunter.domain.dto.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.jts.mediahunter.domain.mappers.ChannelMapper;
import com.jts.mediahunter.domain.mappers.MultimediumMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Tony
 */
@Component
@Slf4j
public class AdministrationFacadeImpl implements AdministrationFacade {

    @Autowired
    private ChannelMapper channelMapper;

    @Autowired
    private MultimediumMapper multimediumMapper;

    @Autowired
    private DatabaseService db;

    @Autowired
    private PluginService plugins;

    private ChannelPreviewDTO channelToFindChannelDTO(Channel channel) {
        return channelMapper.channelToChannelPreviewDTO(channel);
    }

    @Override
    public List<ChannelPreviewDTO> getChannelsByExternalId(String externalId) {
        List<Channel> dbChannels = db.getChannelsByExternalId(externalId);
        List<Channel> pluginChannels = plugins.getChannelsByExternalId(externalId);

        List<ChannelPreviewDTO> foundChannels = new ArrayList<>();

        for (int i = 0; i < dbChannels.size(); i++) {
            for (int j = 0; j < pluginChannels.size(); i++) {
                if (dbChannels.get(i).isSameAs(pluginChannels.get(j))) {
                    pluginChannels.remove(j);
                    break;
                }
            }
            foundChannels.add(channelToFindChannelDTO(dbChannels.get(i)));
        }

        for (Channel pluginChannel : pluginChannels) {
            foundChannels.add(channelToFindChannelDTO(pluginChannel));
        }

        return foundChannels;
    }

    @Override
    public ChannelInfoDTO putChannelToDB(String externalId, String mcpName, boolean trusted) {
        if (!RequestStorage.isPresent(externalId, mcpName)) {
            RequestStorage.addToChannelStorage(externalId, mcpName);
            Channel channel = plugins.getChannelByExternalId(externalId, mcpName);
            channel.setTrusted(trusted);
            List<Multimedium> multimedia = plugins.getMultimediaByUploaderExternalId(externalId, mcpName);
            List<Multimedium> multimediaDB = db.getMultimediaByUploaderExtednalId(externalId)
                    .stream()
                    .filter(r -> r.getMcpName().equals(mcpName))
                    .collect(Collectors.toList());

            for (int i = 0; i < multimedia.size(); i++) {
                boolean removed = false;
                for (int j = 0; j < multimediaDB.size(); j++) {
                    if (multimediaDB.get(j).isSameAs(multimedia.get(i))) {
                        removed = true;
                        if (trusted && multimediaDB.get(j).getStage() != RecordStage.REJECTED) {
                            db.acceptMultimedium(multimediaDB.get(j));
                        }
                        channel.registerNewMultimedium(multimediaDB.get(j));
                        break;
                    }
                }
                if (removed) {
                    continue;
                }
                db.putMultimediumToDB(multimedia.get(i));
                if (trusted) {
                    db.acceptMultimedium(multimedia.get(i));
                }
                channel.registerNewMultimedium(multimedia.get(i));
            }

            channel = db.putChannelToDB(channel);
            RequestStorage.removeFromChannelStorage(externalId, mcpName);
            return channelMapper.channelToChannelInfoDTO(channel);
        }
        return null;
    }

    @Override
    public ChannelInfoDTO getChannelInfo(String internalId) {
        Channel channel = db.getChannelById(internalId);
        ChannelInfoDTO info = channelMapper.channelToChannelInfoDTO(channel);
        return info;
    }

    @Override
    public void updateChannel(String internalId) {
        Channel channel = db.getChannelById(internalId);
        Channel updatedChannel = plugins.getChannelByExternalId(channel.getExternalId(), channel.getMcpName());

        List<Multimedium> knownMultimedia = db.getMultimediaByUploaderExtednalId(channel.getExternalId());
        List<Multimedium> allMultimedia = plugins.getMultimediaByUploaderExternalId(channel.getExternalId(), channel.getMcpName());

        for (int i = 0; i < allMultimedia.size(); i++) {
            boolean shouldbreak = false;
            for (Multimedium multimedium: knownMultimedia) {
                if (allMultimedia.get(i).isSameAs(multimedium)) {
                    allMultimedia.remove(i);
                    shouldbreak = true;
                    break;
                }
            }
            if (shouldbreak) {
                i--;
            }
        }

        for (Multimedium multimedium: allMultimedia) {
            db.putMultimediumToDB(multimedium);
            if (channel.isTrusted()) {
                db.acceptMultimedium(multimedium);
            }
            channel.registerNewMultimedium(multimedium);
        }

        channel.setName(updatedChannel.getName());
        db.updateChannel(channel);
    }

    @Override
    public void deleteChannel(String internalId, boolean deleteAllChannelMultimedia) {
        db.deleteChannel(internalId, deleteAllChannelMultimedia);
    }

    private MultimediumPreviewDTO multimediumToMultimediumPreviewDTO(Multimedium multimedium) {
        return multimediumMapper.multimediumToMultimediumPreviewDTO(multimedium);
    }

    @Override
    public List<MultimediumPreviewDTO> getMultimediaByExternalId(String externalId) {
        List<Multimedium> dbMultimedia = db.getMultimediaByExternalId(externalId);
        List<Multimedium> pluginMultimedia = plugins.getMultimediaByExternalId(externalId);

        List<MultimediumPreviewDTO> foundMultimedia = new ArrayList<>();

        for (int i = 0; i < dbMultimedia.size(); i++) {
            for (int j = 0; j < pluginMultimedia.size(); j++) {
                if (dbMultimedia.get(i).isSameAs(pluginMultimedia.get(j))) {
                    pluginMultimedia.remove(j);
                    break;
                }
            }
            foundMultimedia.add(multimediumToMultimediumPreviewDTO(dbMultimedia.get(i)));
        }

        for (Multimedium pluginMultimedium : pluginMultimedia) {
            foundMultimedia.add(multimediumToMultimediumPreviewDTO(pluginMultimedium));
        }

        return foundMultimedia;
    }

    @Override
    public String putMultimediumToDB(String externalId, String mcpName) {
        Multimedium multimedium = plugins.getMultimediumByExternalId(externalId, mcpName);
        String id = db.putMultimediumToDB(multimedium);
        acceptMultimedium(id);
        return id;
    }

    @Override
    public void acceptMultimedium(String internalId) {
        Multimedium multimedium = db.getMultimediumById(internalId);
        db.acceptMultimedium(multimedium);
    }

    @Override
    public MultimediumInfoDTO getMultimediumInfo(String internalId) {
        Multimedium multimedium = db.getMultimediumById(internalId);
        return multimediumMapper.multimediumToMultimediumInfoDTO(multimedium);
    }

    @Override
    public void updateMultimedium(String internalId) {
        Multimedium multimedium = db.getMultimediumById(internalId);
        Multimedium updatedMultimedium = plugins.getMultimediumByExternalId(multimedium.getExternalId(), multimedium.getMcpName());

        multimedium.setDescription(updatedMultimedium.getDescription());
        multimedium.setName(updatedMultimedium.getName());
        multimedium.setThumbnail(updatedMultimedium.getThumbnail());
        multimedium.setUri(updatedMultimedium.getUri());

        db.updateMultimedium(multimedium);
    }

    @Override
    public List<MultimediumPreviewDTO> getWaitingMultimedia() {
        List<Multimedium> multimedia = db.getWaitingMultimedia();
        List<MultimediumPreviewDTO> found = new ArrayList<>();
        for (Multimedium multimedium : multimedia) {
            found.add(multimediumToMultimediumPreviewDTO(multimedium));
        }
        return found;
    }

    @Override
    public void rejectMultimedium(String internalId) {
        Multimedium multimedium = db.getMultimediumById(internalId);
        db.rejectMultimedium(multimedium);
    }

    @Override
    public void addAllNewMedia() {
        List<Channel> channels = db.getAllChannels();
        for (Channel channel : channels) {
            List<Multimedium> multimedia = plugins.getMultimediaByUploaderExternalId(channel.getExternalId(), channel.getMcpName(), channel.getLastMultimediumUpload());
            for (Multimedium multimedium : multimedia) {
                db.putMultimediumToDB(multimedium);
                if (channel.isTrusted()) {
                    db.acceptMultimedium(multimedium);
                }
                channel.registerNewMultimedium(multimedium);
                db.updateChannel(channel);
            }

        }
    }

    @Override
    public List<PublicMultimediumDTO> getMultimediaPage(int page) {
        List<Multimedium> multimedia = db.getMultimediaPage(page);
        List<PublicMultimediumDTO> rec = new ArrayList<>();
        for (Multimedium multimedium :
                multimedia) {
            rec.add(multimediumMapper.multimediumToPublicMultimediumDTO(multimedium));
        }
        return rec;
    }

    @Override
    public List<PublicChannelDTO> getTrustedChannels() {
        List<Channel> channels = db.getTrustedChannels();
        List<PublicChannelDTO> trustedChannels = new ArrayList<>();
        for (Channel channel: channels) {
            trustedChannels.add(channelMapper.channelToPublicChannelDTO(channel));
        }
        return trustedChannels;
    }

}
