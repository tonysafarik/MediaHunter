package com.jts.mediahunter.domain.dto;

import java.net.URI;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.Builder;

/**
 * Contains all information about channel entity
 * @author Tony
 */
@Data
@Builder
public class ChannelInfoDTO {
    
    private String id;
    private String externalId;
    private String mcpName;
    private String name;
    private URI uri;
    private long multimediumCount;
    private long acceptedMultimediumCount;
    private LocalDateTime lastMultimediumUpload;
    private boolean trusted;
    
}
