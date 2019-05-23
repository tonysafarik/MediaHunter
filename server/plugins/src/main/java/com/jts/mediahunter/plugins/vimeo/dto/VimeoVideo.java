package com.jts.mediahunter.plugins.vimeo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jts.mediahunter.domain.Thumbnail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.UriComponentsBuilder;
import org.json.JSONArray;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@Slf4j
public class VimeoVideo {

    private String name;
    private String description;
    private String uri;
    private Thumbnail thumbnail;

    @JsonProperty("link")
    private String link;

    @JsonProperty("release_time")
    private String release_time;

    @JsonProperty("user")
    private VimeoUser user;

    @JsonProperty("pictures")
    private void setThumbnails(VimeoPictures pictures) {
        this.thumbnail = new Thumbnail();
        List<VimeoThumbnail> thumbnails = Arrays.asList(pictures.getSizes());
        VimeoThumbnail low = thumbnails
                .stream()
                .filter((pic) -> pic.getHeight() > 200 && pic.getHeight() < 600)
                .max(Comparator.comparingInt(VimeoThumbnail::getHeight))
                .orElse(null);
        VimeoThumbnail high = thumbnails
                .stream()
                .filter((pic) -> pic.getHeight() > 600 && pic.getHeight() < 1000)
                .max(Comparator.comparingInt(VimeoThumbnail::getHeight))
                .orElse(null);
        if (low != null) this.thumbnail.setLowResolution(low.getLink());
        if (high != null) this.thumbnail.setHighResolution(high.getLink());
    }

    public String getDescription() {
        return this.description;
    }

    public String getExternalId() {
        return this.uri.replace("/videos/", "");
    }

    public String getName() {
        return this.name;
    }

    public Thumbnail getThumbnail() {
        return this.thumbnail;
    }

    public LocalDateTime getUploadTime() {
        return LocalDateTime.parse(this.release_time, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    public String getUploaderExternalId() {
        return user.getExternalId();
    }

    public URI getUri() {
        return UriComponentsBuilder.fromUriString(this.link).build().toUri();
    }

}

