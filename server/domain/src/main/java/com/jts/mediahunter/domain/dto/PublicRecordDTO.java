package com.jts.mediahunter.domain.dto;

import com.jts.mediahunter.domain.Thumbnail;
import lombok.Builder;
import lombok.Data;

import java.net.URI;
import java.sql.Timestamp;

@Data
@Builder
public class PublicRecordDTO {

    private String id;
    private String externalId;
    private String uploaderExternalId;
    private String name;
    private String mcpName;
    private URI uri;
    private Thumbnail thumbnail;
    private Timestamp uploadTime;
    private String description;

}
