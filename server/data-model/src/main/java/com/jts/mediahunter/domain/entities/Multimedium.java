package com.jts.mediahunter.domain.entities;

import com.jts.mediahunter.domain.RecordStage;
import com.jts.mediahunter.domain.Thumbnail;
import java.net.URI;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;

/**
 * Entity for every Multimedium of a Channel (Multimedium can be video, audio, ...)
 *
 * @author Tony
 */
@Data
@Builder
@Entity
//@Table(indexes = {
//        @Index(name = "upload_time_index", columnList = "upload_time"),
//        @Index(name = "stage_index", columnList = "stage")
//})
public class Multimedium {

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
    private String name;

    /**
     * name of the media content provider (MCP) (must be unified for all media
     * from one service!)
     */
    @NonNull
    private String mcpName;

    /**
     * URI of the multimedium
     */
    @NonNull
    private URI uri;

    /**
     * URIs of thumbnail pictures (if available)
     */
    @Embedded
    private Thumbnail thumbnail;

    /**
     * date and time of upload of the multimedium (used for sorting)
     */
    @NonNull
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime uploadTime;

    /**
     * Description pulled from the multimedium information, can be null
     */
    private String description;

    @NonNull
    @Builder.Default
    @Enumerated(EnumType.ORDINAL)
    private RecordStage stage = RecordStage.UNKNOWN;
    
    /**
     * Check if multimedium is the same as this object (only using external ID and
     * name of MCP). Multimedium from DB and from Plugin can be the same (even though
     * Multimedium entity from Plugin doesn't have it's internal ID set).
     *
     * @param multimedium
     * @return
     */
    public boolean isSameAs(Multimedium multimedium) {
        return multimedium.getExternalId().equals(this.externalId) && multimedium.getMcpName().equals(this.mcpName);
    }

}
