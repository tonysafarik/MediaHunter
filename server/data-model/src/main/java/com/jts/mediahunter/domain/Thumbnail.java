package com.jts.mediahunter.domain;

import java.net.URI;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.Embeddable;

/**
 *
 * @author Tony
 */
@Data
@NoArgsConstructor
@Embeddable
public class Thumbnail {
    
    private URI lowResolution;
    
    private URI highResolution;

    public Thumbnail(String lowResolution, String highResolution) {
        this.lowResolution = UriComponentsBuilder.fromUriString(lowResolution).build().toUri();
        this.highResolution = UriComponentsBuilder.fromUriString(highResolution).build().toUri();
    }

    public void setLowResolution(String lowResolution) {
        this.lowResolution = UriComponentsBuilder.fromUriString(lowResolution).build().toUri();
    }

    public void setHighResolution(String highResolution) {
        this.highResolution = UriComponentsBuilder.fromUriString(highResolution).build().toUri();
    }

    public void setLowResolution(URI lowResolution) {
        this.lowResolution = lowResolution;
    }

    public void setHighResolution(URI highResolution) {
        this.highResolution = highResolution;
    }
}
