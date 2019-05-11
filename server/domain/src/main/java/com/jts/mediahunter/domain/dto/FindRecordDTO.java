package com.jts.mediahunter.domain.dto;

import java.net.URI;

import com.jts.mediahunter.domain.RecordStage;
import lombok.Data;

/**
 *
 * @author Tony
 */
@Data
public class FindRecordDTO {
    
    private String id;
    
    private String externalId;
    
    private String name;
    
    private URI uri;
    
    private String mcpName;

    private RecordStage stage;
    
}
