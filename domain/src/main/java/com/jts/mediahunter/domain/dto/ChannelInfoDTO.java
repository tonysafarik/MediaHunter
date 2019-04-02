package com.jts.mediahunter.domain.dto;

import java.net.URI;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.Builder;

/**
 *
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
    private long recordCount;
    private long acceptedRecordCount;
    private LocalDateTime lastRecordUpload;
    private boolean trusted;
    
}
