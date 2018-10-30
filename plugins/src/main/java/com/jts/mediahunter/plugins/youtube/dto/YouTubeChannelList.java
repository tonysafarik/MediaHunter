package com.jts.mediahunter.plugins.youtube.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Tony
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class YouTubeChannelList {
    
    private int totalResults;
    private int resultsPerPage;
    
    @JsonProperty("pageInfo")
    private void getPageInfo(Map<String, Integer> pageInfo){
        this.totalResults = pageInfo.get("totalResults");
        this.resultsPerPage = pageInfo.get("resultsPerPage");
    }
    
    List<YouTubeChannel> channels;
    
    @JsonProperty("items")
    private void getItems(YouTubeChannel[] channels){
        this.channels = new ArrayList<>();
        this.channels.addAll(Arrays.asList(channels));
    }

    public List<YouTubeChannel> getChannels() {
        return this.channels;
    }

    public int getTotalResults() {
        return this.totalResults;
    }

    public int getResultsPerPage() {
        return this.resultsPerPage;
    }
    
}
