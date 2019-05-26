package com.jts.mediahunter.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PublicRecordListDTO {

    private List<PublicMultimediumDTO> list;

}
