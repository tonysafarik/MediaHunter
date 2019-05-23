package com.jts.mediahunter.domain.mappers;

import com.jts.mediahunter.domain.dto.MultimediumPreviewDTO;
import com.jts.mediahunter.domain.dto.PublicRecordDTO;
import com.jts.mediahunter.domain.dto.RecordInfoDTO;
import com.jts.mediahunter.domain.entities.Record;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RecordMapper {

    MultimediumPreviewDTO recordToFindRecordDTO(Record record);

    RecordInfoDTO recordToRecordInfoDTO(Record record);

    @Mapping(target = "uploadTime", expression = "java(java.sql.Timestamp.valueOf(record.getUploadTime()))")
    PublicRecordDTO recordToPublicRecordDTO(Record record);

}
