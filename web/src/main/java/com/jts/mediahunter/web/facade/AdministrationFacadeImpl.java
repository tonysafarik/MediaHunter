package com.jts.mediahunter.web.facade;

import com.jts.mediahunter.core.service.DatabaseService;
import com.jts.mediahunter.core.service.PluginService;
import com.jts.mediahunter.domain.RecordStage;
import com.jts.mediahunter.domain.entities.Channel;
import com.jts.mediahunter.domain.entities.Record;
import com.jts.mediahunter.web.dto.ChannelInfoDTO;
import com.jts.mediahunter.web.dto.FindChannelDTO;
import com.jts.mediahunter.web.dto.FindRecordDTO;
import com.jts.mediahunter.web.dto.RecordInfoDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Tony
 */
@Component
public class AdministrationFacadeImpl implements AdministrationFacade {

    @Autowired
    private DatabaseService db;

    @Autowired
    private PluginService plugins;

    private FindChannelDTO channelToFindChannelDTO(Channel channel) {
        return FindChannelDTO.builder()
                .mcpName(channel.getNameOfMcp())
                .channelName(channel.getNameOfChannel())
                .externalId(channel.getExternalId())
                .internalId(channel.getId())
                .trusted(channel.isTrusted())
                .uri(channel.getUri())
                .build();
    }

    @Override
    public List<FindChannelDTO> getChannelsByExternalId(String externalId) {
        List<Channel> dbChannels = db.getChannelsByExternalId(externalId);
        List<Channel> pluginChannels = plugins.getChannelsByExternalId(externalId);
        List<FindChannelDTO> foundChannels = new ArrayList<>();

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
    public String putChannelToDB(String externalId, String mcpName, boolean trusted) {
        Channel channel = plugins.getChannelByExternalIdFromMCP(externalId, mcpName);
        channel.setTrusted(trusted);
        if (trusted) {
            //TODO add all its videos to accepted stage
        }
        return db.putChannelToDB(channel);
    }

    @Override
    public ChannelInfoDTO getChannelInfo(String internalId) {
        db.getChannelById(internalId);
        //TODO decide what info do I need
        return new ChannelInfoDTO();
    }

    @Override
    public void updateChannel(String internalId) {
        Channel channel = db.getChannelById(internalId);
        Channel updatedChannel = plugins.getChannelByExternalIdFromMCP(channel.getExternalId(), channel.getNameOfMcp());
        channel.setNameOfChannel(updatedChannel.getNameOfChannel());
        // update more if needed
        db.updateChannel(channel);
    }

    @Override
    public void deleteChannel(String internalId, boolean deleteAllChannelRecords) {
        db.deleteChannel(internalId, deleteAllChannelRecords);
    }

    private FindRecordDTO recordToFindRecordDTO(Record record) {
        FindRecordDTO recordDTO = new FindRecordDTO();
        recordDTO.setExternalId(record.getExternalId());
        recordDTO.setInternalId(record.getId());
        recordDTO.setMcpName(record.getNameOfMcp());
        recordDTO.setName(record.getNameOfRecord());
        recordDTO.setUri(record.getUri());
        return recordDTO;
    }

    @Override
    public List<FindRecordDTO> getRecordsByExternalId(String externalId) {
        List<Record> dbRecords = db.getRecordsByExternalId(externalId);
        List<Record> pluginRecords = plugins.getRecordsByExternalId(externalId);

        List<FindRecordDTO> foundRecords = new ArrayList<>();

        for (int i = 0; i < dbRecords.size(); i++) {
            for (int j = 0; j < pluginRecords.size(); j++) {
                if (dbRecords.get(i).isSameAs(pluginRecords.get(j))) {
                    pluginRecords.remove(j);
                    break;
                }
            }
            foundRecords.add(recordToFindRecordDTO(dbRecords.get(i)));
        }

        for (Record pluginRecord : pluginRecords) {
            foundRecords.add(recordToFindRecordDTO(pluginRecord));
        }

        return foundRecords;
    }

    @Override
    public String putRecordToDB(String externalId, String mcpName) {
        Record record = plugins.getRecordByExternalIdFromMCP(externalId, mcpName);
        String id = db.putRecordToDB(record);
        acceptRecord(id);
        return id;
    }

    private void acceptRecord(String internalId) {
        Record record = db.getRecordById(internalId);
        switch (record.getStage()) {
            case ACCEPTED:
                break;
            case WAITING:
            case REJECTED:
                db.acceptRecord(record);
                break;
            default:
                break;
        }
    }

    @Override
    public RecordInfoDTO getRecordInfo(String internalId) {
        Record record = db.getRecordById(internalId);
        RecordInfoDTO info = RecordInfoDTO.builder()
                .description(record.getDescription())
                .externalId(record.getExternalId())
                .internalId(record.getId())
                .mcpName(record.getNameOfMcp())
                .name(record.getNameOfRecord())
                .stage(record.getStage())
                .thumbnails(record.getThumbnail())
                .uploaderExternalId(record.getUploaderExternalId())
                .uri(record.getUri())
                .build();
        return info;
    }

    @Override
    public void updateRecord(String internalId) {
        Record record = db.getRecordById(internalId);
        Record updatedRecord = plugins.getRecordByExternalIdFromMCP(record.getExternalId(), record.getNameOfMcp());
        record.setDescription(updatedRecord.getDescription());
        record.setNameOfRecord(updatedRecord.getNameOfRecord());
        record.setThumbnail(updatedRecord.getThumbnail());
        record.setUri(updatedRecord.getUri());
        db.updateRecord(record);
    }

}
