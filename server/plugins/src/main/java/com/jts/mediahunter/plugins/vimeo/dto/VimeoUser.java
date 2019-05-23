package com.jts.mediahunter.plugins.vimeo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@JsonIgnoreProperties(ignoreUnknown = true, value = "user")
public class VimeoUser {

    private String name;
    private String uri;

    @JsonProperty("link")
    private String link;

    public String getExternalId() {
        return uri.replace("/users/", "");
    }

    public String getName() {
        return this.name;
    }

    public URI getUri() {
        return UriComponentsBuilder.fromUriString(this.link).build().toUri();
    }
}
