package com.jts.mediahunter.plugins.vimeo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class VimeoThumbnail {

    private int width;
    private int height;
    private String link;

}
