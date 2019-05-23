package com.jts.mediahunter.plugins.vimeo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@Slf4j
@Getter
public class VimeoVideoList {

    @JsonProperty("page")
    private int page;

    private String nextPage;
    private String lastPage;
    List<VimeoVideo> videos;

    @JsonProperty("paging")
    private void setPages(Map<String, Object> paging) {
        this.nextPage = (String) paging.get("next");
        this.lastPage = (String) paging.get("last");
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    @JsonProperty("data")
    private void setVideos(VimeoVideo[] data) {
        this.videos = new ArrayList<>();
        videos.addAll(Arrays.asList(data));
    }

}
