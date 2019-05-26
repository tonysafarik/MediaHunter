package com.jts.mediahunter.plugins.vimeo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class VimeoPictures {

    @JsonProperty("sizes")
    private VimeoThumbnail[] sizes;

}
