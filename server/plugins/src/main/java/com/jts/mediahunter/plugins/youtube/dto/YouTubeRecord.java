package com.jts.mediahunter.plugins.youtube.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jts.mediahunter.domain.Thumbnail;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author Tony
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class YouTubeRecord {

    @JsonProperty
    private String id;
    private String name;
    private String description;
    private String uploaderExternalId;
    private Thumbnail thumbnail;
    private LocalDateTime uploadTime;

    private final String videoUriBuilder = "https://www.youtube.com/watch?v=";
    
    @JsonProperty("snippet")
    private void getSnippetInfo(Map<String, Object> snippet) {
        this.name = String.valueOf(snippet.get("title"));
        this.description = String.valueOf(snippet.get("description"));
        this.uploaderExternalId = String.valueOf(snippet.get("channelId"));
        this.uploadTime = LocalDateTime.parse(String.valueOf(snippet.get("publishedAt")), DateTimeFormatter.ISO_ZONED_DATE_TIME);
        setThumbnail((Map<String, Object>) snippet.get("thumbnails"));
        if (snippet.containsKey("resourceId")) {
            resetVideoId((Map<String, Object>) snippet.get("resourceId"));
        }
    }

    /**
     * DATA FROM 29.10.2018: YouTube video thumbnail sizes: </br>
     * "default": 120x90 px </br>
     * "medium": 320x180 px (saved as Low Resolution) </br>
     * "high": 480x360 px </br>
     * "standard": 640x480 px (saved as High Resolution) </br>
     * "maxres": 1280x720 px
     *
     * @param thumbnails
     */
    private void setThumbnail(Map<String, Object> thumbnails) {
        URI lowRes = getThumbnailResolutionURI("medium", thumbnails);
        URI highRes = getThumbnailResolutionURI("standard", thumbnails);
        if (lowRes != null || highRes != null) {
            if (this.thumbnail == null) {
                this.thumbnail = new Thumbnail();
            }
            this.thumbnail.setLowResolution(lowRes);
            this.thumbnail.setHighResolution(highRes);
        }
    }

    private URI getThumbnailResolutionURI(String res, Map<String, Object> thumbnails) {
        if (thumbnails.containsKey(res)) {
            return UriComponentsBuilder
                    .fromUriString(String.valueOf(((Map<String, Object>) thumbnails.get(res)).get("url")))
                    .build()
                    .toUri();
        }
        return null;
    }

    /**
     * Used only if there is resourceId object inside the JSON (resourceId is
     * present if the JSON is youtube#playlistItemListResponse and not
     * youtube#videoListResponse)
     *
     * @param resourceId
     */
    private void resetVideoId(Map<String, Object> resourceId) {
        this.id = (String) resourceId.get("videoId");
    }

    public String getVideoId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Thumbnail getThumbnail() {
        return this.thumbnail;
    }

    public String getDescription() {
        return this.description;
    }

    public String getUploaderExternalId() {
        return this.uploaderExternalId;
    }

    public LocalDateTime getUploadTime() {
        return this.uploadTime;
    }

    public URI getURI() {
        return UriComponentsBuilder.fromUriString(this.videoUriBuilder + this.id).build().toUri();
    }

}
