package com.jts.mediahunter.domain;

import java.net.URI;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author Tony
 */
@Data
@NoArgsConstructor
public class Thumbnail {
    
    private URI lowResolution;
    
    private URI highResolution;

    public Thumbnail(String lowResolution, String highResolution) {
        this.lowResolution = UriComponentsBuilder.fromUriString(lowResolution).build().toUri();
        this.highResolution = UriComponentsBuilder.fromUriString(highResolution).build().toUri();
    }
    
}
