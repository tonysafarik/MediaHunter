package com.jts.mediahunter.domain.entities;

import com.jts.mediahunter.domain.RecordStage;
import com.jts.mediahunter.domain.Thumbnail;
import java.net.URI;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entity for every Record of a Channel (Record can be video, audio, ...)
 *
 * @author Tony
 */
@Data
@Builder
@Document
public class Record {

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

    /**
     * ID of the uploader Channel provided by the media content provider (MCP)
     */
    @NonNull
    private String uploaderExternalId;

    @NonNull
    private String nameOfRecord;

    /**
     * name of the media content provider (MCP) (must be unified for all media
     * from one service!)
     */
    @NonNull
    private String nameOfMcp;

    /**
     * URI of the record
     */
    @NonNull
    private URI uri;

    /**
     * URIs of thumbnail pictures (if available)
     */
    private Thumbnail thumbnail;

    /**
     * date and time of upload of the record (used for sorting)
     */
    @NonNull
    @Indexed(direction = IndexDirection.DESCENDING, name = "uploadTime")
    private LocalDateTime uploadTime;

    /**
     * Description pulled from the record information, can be null
     */
    private String description;

    @NonNull
    @Builder.Default
    private RecordStage stage = RecordStage.UNKNOWN;
    
    /**
     * Check if record is the same as this object (only using external ID and
     * name of MCP). Record from DB and from Plugin can be the same (even though
     * Record entity from Plugin doesn't have it's internal ID set).
     *
     * @param record
     * @return
     */
    public boolean isSameAs(Record record) {
        return record.getExternalId().equals(this.externalId) && record.getNameOfMcp().equals(this.nameOfMcp);
    }

}
