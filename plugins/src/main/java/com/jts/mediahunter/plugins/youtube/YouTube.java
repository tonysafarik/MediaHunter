package com.jts.mediahunter.plugins.youtube;

import com.jts.mediahunter.domain.entities.Channel;
import com.jts.mediahunter.domain.entities.Record;
import com.jts.mediahunter.plugins.MediaContentProviderPlugin;
import com.jts.mediahunter.plugins.youtube.dto.YouTubeChannelList;
import com.jts.mediahunter.plugins.youtube.dto.YouTubeRecordList;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author Tony
 */
@Repository
public class YouTube implements MediaContentProviderPlugin {

    @Autowired
    private RestTemplate rest;

    /**
     * Preferred way to add name of service - service name must be always the
     * same for one plugin
     */
    private final String SERVICE_NAME = "YouTube";

    /**
     * Value imported from secret.properties
     */
    @Value("${youtube.apikey}")
    private String API_KEY;

    private final String API_URI = "https://www.googleapis.com/youtube/v3";

    @Override
    public List<Channel> getChannelByExternalId(String channelId) {
        String[] parameters = {"part", "snippet", "id", channelId, "maxResults", "50", "key", this.API_KEY};

        URI uri = buildURIForHTTPRequest("/channels", parameters);

        YouTubeChannelList channelList = rest.getForObject(uri, YouTubeChannelList.class);

        List<Channel> channels = new ArrayList<>();

        if (channelList != null) {
            channelList.getChannels().forEach((channel) -> {
                channels.add(
                        Channel.builder()
                                .externalId(channel.getExternalId())
                                .nameOfChannel(channel.getName())
                                .nameOfMcp(SERVICE_NAME)
                                .uri(channel.getUri())
                                .build()
                );
            });
        }
        
        return channels;
    }

    /**
     * Make sure there's always even number of elements in parameters array, or
     * re-write this method to use Map (or construct your URIs for REST calls
     * some other way)
     *
     * @param path
     * @param parameters
     * @return
     */
    private URI buildURIForHTTPRequest(String path, String[] parameters) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(API_URI);
        builder.path(path);
        for (int i = 0; i < parameters.length; i += 2) {
            builder.queryParam(parameters[i], parameters[i + 1]);
        }
        URI uri = builder.build().toUri();
        return uri;
    }

    @Override
    public List<Record> getRecordByExternalId(String recordId) {
        String[] parameters = {"part", "snippet", "id", recordId, "key", API_KEY};

        URI uri = buildURIForHTTPRequest("/videos", parameters);

        YouTubeRecordList videoList = rest.getForObject(uri, YouTubeRecordList.class);

        List<Record> videos = new ArrayList<>();

        if (videoList != null) {
            videoList.getRecords().forEach((video) -> {
                videos.add(Record.builder()
                        .uploaderExternalId(video.getUploaderExternalId())
                        .description(video.getDescription())
                        .nameOfRecord(video.getName())
                        .nameOfMcp(SERVICE_NAME)
                        .externalId(video.getVideoId())
                        .thumbnail(video.getThumbnail())
                        .uploadTime(video.getUploadTime())
                        .uri(video.getURI())
                        .build());
            });
        }

        return videos;
    }

    @Override
    public String getMcpName() {
        return this.SERVICE_NAME;
    }

}
