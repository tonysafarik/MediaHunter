package com.jts.mediahunter.domain.dto;

import java.net.URI;
import lombok.Builder;
import lombok.Data;

/**
 *
 * @author Tony
 */
@Data
@Builder
public class FindChannelDTO {
    
    private String id;
    
    private String externalId;
    
    private String name;
    
    private URI uri;
    
    private String mcpName;
    
    @Builder.Default private boolean trusted = false;
        
}
