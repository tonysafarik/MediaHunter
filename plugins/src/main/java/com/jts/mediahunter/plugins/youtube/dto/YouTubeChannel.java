package com.jts.mediahunter.plugins.youtube.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.net.URI;
import java.util.Map;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author Tony
 */
@JsonIgnoreProperties(ignoreUnknown = true, value = "channelUriBuilder")
public class YouTubeChannel {
    
    @JsonProperty
    private String id;
    
    private String name;
        
    private final String channelUriBuilder = "https://www.youtube.com/channel/";
    
    @JsonProperty("snippet")
    private void getSnippet(Map<String, Object> snippet){
        this.name = (String.valueOf(snippet.get("title")));
    }
        
    public String getExternalId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public URI getUri() {
        return UriComponentsBuilder.fromUriString(channelUriBuilder + id).build().toUri();
    }
    
}
