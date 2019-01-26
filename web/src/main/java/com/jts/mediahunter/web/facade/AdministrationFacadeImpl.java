package com.jts.mediahunter.web.facade;

import com.jts.mediahunter.core.service.DatabaseService;
import com.jts.mediahunter.core.service.PluginService;
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
                .name(channel.getNameOfChannel())
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
        Channel channel = plugins.getChannelByExternalId(externalId, mcpName);
        channel.setTrusted(trusted);
        List<Record> records = plugins.getRecordsByUploaderExternalId(externalId, mcpName);
        channel.setRecordCount(Long.valueOf(records.size()));
        for (Record record : records) {
            db.putRecordToDB(record);
            if (trusted) {
                db.acceptRecord(record);
            }
        }
        return db.putChannelToDB(channel);
    }

    @Override
    public ChannelInfoDTO getChannelInfo(String internalId) {
        Channel channel = db.getChannelById(internalId);
        ChannelInfoDTO info = ChannelInfoDTO.builder()
                .acceptedRecordCount(channel.getAcceptedRecordCount())
                .externalId(channel.getExternalId())
                .internalId(channel.getId())
                .lastRecordUploadTime(channel.getLastRecordUpload())
                .mcpName(channel.getNameOfMcp())
                .name(channel.getNameOfChannel())
                .recordCount(channel.getRecordCount())
                .trusted(channel.isTrusted())
                .uri(channel.getUri())
                .build();
        return info;
    }

    @Override
    public void updateChannel(String internalId) {
        Channel channel = db.getChannelById(internalId);
        Channel updatedChannel = plugins.getChannelByExternalId(channel.getExternalId(), channel.getNameOfMcp());
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
        Record record = plugins.getRecordByExternalId(externalId, mcpName);
        String id = db.putRecordToDB(record);
        acceptRecord(id);
        return id;
    }

    @Override
    public void acceptRecord(String internalId) {
        Record record = db.getRecordById(internalId);
        db.acceptRecord(record);
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
        Record updatedRecord = plugins.getRecordByExternalId(record.getExternalId(), record.getNameOfMcp());
        record.setDescription(updatedRecord.getDescription());
        record.setNameOfRecord(updatedRecord.getNameOfRecord());
        record.setThumbnail(updatedRecord.getThumbnail());
        record.setUri(updatedRecord.getUri());
        db.updateRecord(record);
    }

    @Override
    public List<FindRecordDTO> getWaitingRecords() {
        List<Record> records = db.getWaitingRecords();
        List<FindRecordDTO> found = new ArrayList<>();
        for (Record record : records) {
            found.add(recordToFindRecordDTO(record));
        }
        return found;
    }

    @Override
    public void rejectRecord(String internalId) {
        Record record = db.getRecordById(internalId);
        db.rejectRecord(record);
    }

    @Override
    public void addAllNewMedia() {
        List<Channel> channels = db.getAllChannels();
        for (Channel channel : channels) {
            List<Record> records = plugins.getRecordsByUploaderExternalId(channel.getExternalId(), channel.getNameOfMcp());
            for (Record record : records) {
                db.putRecordToDB(record);
                if (channel.isTrusted()) {
                    db.acceptRecord(record);
                }
            }

        }
    }

}
