package com.jts.mediahunter.web.dto;

import java.net.URI;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Tony
 */
@Data
public class FindRecordDTO {
    
    private String internalId;
    
    private String externalId;
    
    private String name;
    
    private URI uri;
    
    private String mcpName;
    
}
