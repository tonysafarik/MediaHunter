package com.jts.mediahunter.domain.entities;

import java.net.URI;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entity for Channels from any service that has plugin implemented in the
 * plugins module
 * 
 * @author Tony
 */
@Data
@Builder
@Document
public class Channel {
    
    /**
     * Internal ID
     */
    @Id
    private String id;

    /**
     * ID provided by the media service
     */
    @NonNull
    private String externalId;

    @NonNull
    private String nameOfChannel;

    /**
     * name of the media content provider (MCP) (must be unified for all media from one
     * service!)
     */
    @NonNull
    private String nameOfMcp;

    /**
     * URI of the channel
     */
    @NonNull
    private URI uri;

    /**
     * number of all media on this channel (no matter if accepted or not)
     */
    private Long recordCount = 0l;

    /**
     * number of accepted media from this channel
     */
    private Long acceptedRecordCount = 0l;
    
    /**
     * date and time of last record upload (no matter if waiting, accepted or rejected)
     */
    private LocalDateTime lastRecordUpload;
    
}
