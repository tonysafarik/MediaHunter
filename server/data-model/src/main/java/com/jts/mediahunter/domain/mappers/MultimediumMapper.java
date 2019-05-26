package com.jts.mediahunter.domain.mappers;

import com.jts.mediahunter.domain.dto.MultimediumInfoDTO;
import com.jts.mediahunter.domain.dto.MultimediumPreviewDTO;
import com.jts.mediahunter.domain.dto.PublicMultimediumDTO;
import com.jts.mediahunter.domain.entities.Multimedium;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MultimediumMapper {

    MultimediumPreviewDTO multimediumToMultimediumPreviewDTO(Multimedium multimedium);

    MultimediumInfoDTO multimediumToMultimediumInfoDTO(Multimedium multimedium);

    @Mapping(target = "uploadTime", expression = "java(java.sql.Timestamp.valueOf(multimedium.getUploadTime()))")
    PublicMultimediumDTO multimediumToPublicMultimediumDTO(Multimedium multimedium);

}
