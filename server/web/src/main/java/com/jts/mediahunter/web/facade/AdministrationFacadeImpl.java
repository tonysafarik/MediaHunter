package com.jts.mediahunter.web.facade;

import com.jts.mediahunter.core.service.DatabaseService;
import com.jts.mediahunter.core.service.PluginService;
import com.jts.mediahunter.domain.RecordStage;
import com.jts.mediahunter.domain.entities.Channel;
import com.jts.mediahunter.domain.entities.Record;
import com.jts.mediahunter.domain.dto.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.jts.mediahunter.domain.mappers.ChannelMapper;
import com.jts.mediahunter.domain.mappers.RecordMapper;
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
    private RecordMapper recordMapper;

    @Autowired
    private DatabaseService db;

    @Autowired
    private PluginService plugins;

    private FindChannelDTO channelToFindChannelDTO(Channel channel) {
        return channelMapper.channelToFindChannelDTO(channel);
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
    public ChannelInfoDTO putChannelToDB(String externalId, String mcpName, boolean trusted) {
        if (!RequestStorage.isPresent(externalId, mcpName)) {
            RequestStorage.addToChannelStorage(externalId, mcpName);
            Channel channel = plugins.getChannelByExternalId(externalId, mcpName);
            channel.setTrusted(trusted);
            List<Record> records = plugins.getRecordsByUploaderExternalId(externalId, mcpName);
            List<Record> recordsDB = db.getMultimediaByUploaderExtednalId(externalId)
                    .stream()
                    .filter(r -> r.getMcpName().equals(mcpName))
                    .collect(Collectors.toList());

            for (int i = 0; i < records.size(); i++) {
                boolean removed = false;
                for (int j = 0; j < recordsDB.size(); j++) {
                    if (recordsDB.get(j).isSameAs(records.get(i))) {
                        removed = true;
                        if (trusted && recordsDB.get(j).getStage() != RecordStage.REJECTED) {
                            db.acceptRecord(recordsDB.get(j));
                        }
                        channel.registerNewRecord(recordsDB.get(j));
                        break;
                    }
                }
                if (removed) {
                    continue;
                }
                db.putRecordToDB(records.get(i));
                if (trusted) {
                    db.acceptRecord(records.get(i));
                }
                channel.registerNewRecord(records.get(i));
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
        channel.setName(updatedChannel.getName());
        // update more if needed
        db.updateChannel(channel);
    }

    @Override
    public void deleteChannel(String internalId, boolean deleteAllChannelRecords) {
        db.deleteChannel(internalId, deleteAllChannelRecords);
    }

    private MultimediumPreviewDTO recordToFindRecordDTO(Record record) {
        return recordMapper.recordToFindRecordDTO(record);
    }

    @Override
    public List<MultimediumPreviewDTO> getRecordsByExternalId(String externalId) {
        List<Record> dbRecords = db.getRecordsByExternalId(externalId);
        List<Record> pluginRecords = plugins.getRecordsByExternalId(externalId);

        List<MultimediumPreviewDTO> foundRecords = new ArrayList<>();

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
        return recordMapper.recordToRecordInfoDTO(record);
    }

    @Override
    public void updateRecord(String internalId) {
        Record record = db.getRecordById(internalId);
        Record updatedRecord = plugins.getRecordByExternalId(record.getExternalId(), record.getMcpName());

        record.setDescription(updatedRecord.getDescription());
        record.setName(updatedRecord.getName());
        record.setThumbnail(updatedRecord.getThumbnail());
        record.setUri(updatedRecord.getUri());

        db.updateRecord(record);
    }

    @Override
    public List<MultimediumPreviewDTO> getWaitingRecords() {
        List<Record> records = db.getWaitingRecords();
        List<MultimediumPreviewDTO> found = new ArrayList<>();
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
            List<Record> records = plugins.getRecordsByUploaderExternalId(channel.getExternalId(), channel.getMcpName(), channel.getLastRecordUpload());
            for (Record record : records) {
                db.putRecordToDB(record);
                if (channel.isTrusted()) {
                    db.acceptRecord(record);
                }
                channel.registerNewRecord(record);
            }

        }
    }

    @Override
    public List<PublicRecordDTO> getRecordsPage(int page) {
        List<Record> records = db.getRecordPage(page);
        List<PublicRecordDTO> rec = new ArrayList<>();
        for (Record record :
                records) {
            rec.add(recordMapper.recordToPublicRecordDTO(record));
        }
        return rec;
    }

}
