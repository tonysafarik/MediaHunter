package com.jts.mediahunter.web.dto;

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
    
    private String internalId;
    
    private String externalId;
    
    private String channelName;
    
    private URI uri;
    
    private String mcpName;
    
    @Builder.Default private boolean trusted = false;
        
}
