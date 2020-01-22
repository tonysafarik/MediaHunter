package com.jts.mediahunter.domain.entities;

import java.net.URI;
import java.time.LocalDateTime;

import com.jts.mediahunter.domain.RecordStage;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;

/**
 * Entity for Channels from any service that has plugin implemented in the
 * plugins module
 *
 * @author Tony
 */
@Data
@Builder
@Entity
//@Table(indexes = {
//        @Index(name = "extednal_id_index", columnList = "external_id"),
//        @Index(name = "trusted_index", columnList = "trusted")
//})
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
    private String name;

    /**
     * name of the media content provider (MCP) (must be unified for all media
     * from one service!)
     */
    @NonNull
    private String mcpName;

    /**
     * URI of the channel
     */
    @NonNull
    @Column(columnDefinition = "STRING")
    private URI uri;

    /**
     * number of all media on this channel (no matter if accepted or not)
     */
    @Builder.Default
    private Long multimediumCount = 0l;

    /**
     * number of accepted media from this channel
     */
    @Builder.Default
    private Long acceptedMultimediumCount = 0l;

    /**
     * date and time of last multimedium upload (no matter if waiting, accepted or
     * rejected)
     */
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime lastMultimediumUpload;

    @NonNull
    @Builder.Default
    private boolean trusted = false;

    /**
     * Check if channel is the same as this object (only using external ID and
     * name of MCP). Channel from DB and from Plugin can be the same (even
     * though Channel entity from Plugin doesn't have it's internal ID set).
     *
     * @param channel
     * @return
     */
    public boolean isSameAs(Channel channel) {
        return channel.getExternalId().equals(this.externalId) && channel.getMcpName().equals(this.mcpName);
    }

    public void registerNewMultimedium(Multimedium multimedium) {
        this.multimediumCount++;
        if (multimedium.getStage().equals(RecordStage.ACCEPTED)) {
            registerNewAcceptedMultimedium(multimedium);
        }
        if (this.lastMultimediumUpload == null || multimedium.getUploadTime().isAfter(this.lastMultimediumUpload)) {
            this.lastMultimediumUpload = multimedium.getUploadTime();
        }
    }

    public void registerNewAcceptedMultimedium(Multimedium multimedium) {
        this.acceptedMultimediumCount++;
    }

    public void acceptedMultimediumRejected() {
        this.acceptedMultimediumCount--;
    }

}
