package com.jts.mediahunter.domain.entities;

import java.net.URI;
import java.time.LocalDateTime;

import com.jts.mediahunter.domain.RecordStage;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
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
    @Indexed
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
    private LocalDateTime lastMultimediumUpload;

    @NonNull
    @Builder.Default
    @Indexed
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
