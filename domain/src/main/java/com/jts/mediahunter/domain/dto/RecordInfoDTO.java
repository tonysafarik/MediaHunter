package com.jts.mediahunter.domain.dto;

import com.jts.mediahunter.domain.RecordStage;
import com.jts.mediahunter.domain.Thumbnail;
import java.net.URI;
import lombok.Builder;
import lombok.Data;

/**
 *
 * @author Tony
 */
@Data
@Builder
public class RecordInfoDTO {
    
    private String name;
    private String mcpName;
    private String id;
    private String externalId;
    private String uploaderExternalId;
    private URI uri;
    private Thumbnail thumbnail;
    private String description;
    private RecordStage stage;
    
}
