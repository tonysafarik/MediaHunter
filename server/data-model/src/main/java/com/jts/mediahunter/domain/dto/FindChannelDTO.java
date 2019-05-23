package com.jts.mediahunter.domain.dto;

import java.net.URI;
import lombok.Builder;
import lombok.Data;

/**
 * Contains only information necessary to display simple list of channels
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
