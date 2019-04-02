package com.jts.mediahunter.plugins.youtube;

import com.jts.mediahunter.domain.entities.Channel;
import com.jts.mediahunter.domain.entities.Record;
import com.jts.mediahunter.plugins.MediaContentProviderPlugin;
import com.jts.mediahunter.plugins.youtube.dto.YouTubeChannel;
import com.jts.mediahunter.plugins.youtube.dto.YouTubeChannelList;
import com.jts.mediahunter.plugins.youtube.dto.YouTubeRecord;
import com.jts.mediahunter.plugins.youtube.dto.YouTubeRecordList;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
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
    public Channel getChannelByExternalId(String channelId) {
        String[] parameters = {"part", "snippet", "id", channelId, "maxResults", "50", "key", this.API_KEY};

        URI uri = buildURIForHTTPRequest("/channels", parameters);

        YouTubeChannelList channelList = rest.getForObject(uri, YouTubeChannelList.class);

        if (channelList != null) {
            YouTubeChannel channel = channelList.getChannels().get(0);
            return Channel.builder()
                    .externalId(channel.getExternalId())
                    .name(channel.getName())
                    .mcpName(SERVICE_NAME)
                    .uri(channel.getUri())
                    .build();
        }
        return null;
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
    public Record getRecordByExternalId(String recordId) {
        String[] parameters = {"part", "snippet", "id", recordId, "key", API_KEY};

        URI uri = buildURIForHTTPRequest("/videos", parameters);

        YouTubeRecordList videoList = rest.getForObject(uri, YouTubeRecordList.class);

        if (videoList != null) {
            return youTubeRecordToRecord(videoList.getRecords().get(0));
        }

        return null;
    }

    @Override
    public String getMcpName() {
        return this.SERVICE_NAME;
    }

    private Record youTubeRecordToRecord(YouTubeRecord record) {
        return Record.builder()
                .description(record.getDescription())
                .externalId(record.getVideoId())
                .mcpName(this.SERVICE_NAME)
                .name(record.getName())
                .thumbnail(record.getThumbnail())
                .uploadTime(record.getUploadTime())
                .uploaderExternalId(record.getUploaderExternalId())
                .uri(record.getURI())
                .build();
    }

    @Override
    public List<Record> getAllChannelRecords(String channelId) {
        String playListId = getChannelsUploadListId(channelId);

        if (playListId == null) {
            return new ArrayList<>();
        }

        String[] parameters = {"part", "snippet", "maxResults", "50", "playlistId", playListId, "key", API_KEY};

        URI uri = buildURIForHTTPRequest("/playlistItems", parameters);

        YouTubeRecordList videoList = rest.getForObject(uri, YouTubeRecordList.class);

        List<Record> videos = new ArrayList<>();

        if (videoList == null) {
            return videos;
        }

        for (YouTubeRecord record : videoList.getRecords()) {
            videos.add(youTubeRecordToRecord(record));
        }
        if (videoList.getNextPageTogen() != null) {
            parameters = Arrays.copyOf(parameters, parameters.length + 2);
        }
        while (videoList.getNextPageTogen() != null) {
            parameters[8] = "pageToken";
            parameters[9] = videoList.getNextPageTogen();

            uri = buildURIForHTTPRequest("/playlistItems", parameters);

            videoList = rest.getForObject(uri, YouTubeRecordList.class);
            if (videoList != null) {
                for (YouTubeRecord record : videoList.getRecords()) {
                    videos.add(youTubeRecordToRecord(record));
                }
            }
        }
        return videos;
    }

    private String getChannelsUploadListId(String id) {
        String[] parameters = {"part", "contentDetails", "id", id, "key", API_KEY};

        URI uri = buildURIForHTTPRequest("/channels", parameters);

        Map<String, Object> json = rest.getForObject(uri, Map.class);

        if ((json == null)
                || (!json.containsKey("items"))
                || (((ArrayList) json.get("items")).get(0) == null)
                || (!(json = (Map<String, Object>) ((ArrayList) json.get("items")).get(0)).containsKey("contentDetails"))
                || (!(json = (Map<String, Object>) json.get("contentDetails")).containsKey("relatedPlaylists"))
                || (!(json = (Map<String, Object>) json.get("relatedPlaylists")).containsKey("uploads"))) {
            return null;
        }
        return (String) json.get("uploads");
    }

    @Override
    public List<Record> getNewRecords(String channelId, LocalDateTime oldestRecord) {
        String playListId = getChannelsUploadListId(channelId);

        if (playListId == null) {
            return new ArrayList<>();
        }

        String[] parameters = {"part", "snippet", "maxResults", "50", "playlistId", playListId, "key", API_KEY};

        URI uri = buildURIForHTTPRequest("/playlistItems", parameters);

        YouTubeRecordList videoList = rest.getForObject(uri, YouTubeRecordList.class);

        List<Record> videos = new ArrayList<>();

        if (videoList == null) {
            return videos;
        }

        for (YouTubeRecord record : videoList.getRecords()) {
            if (record.getUploadTime().isAfter(oldestRecord)) {
                videos.add(youTubeRecordToRecord(record));
                continue;
            }
            return videos;
        }
        if (videoList.getNextPageTogen() != null) {
            parameters = Arrays.copyOf(parameters, parameters.length + 2);
        }
        while (videoList.getNextPageTogen() != null) {
            parameters[8] = "pageToken";
            parameters[9] = videoList.getNextPageTogen();

            uri = buildURIForHTTPRequest("/playlistItems", parameters);

            videoList = rest.getForObject(uri, YouTubeRecordList.class);
            if (videoList != null) {
                for (YouTubeRecord record : videoList.getRecords()) {
                    if (record.getUploadTime().isAfter(oldestRecord)) {
                        videos.add(youTubeRecordToRecord(record));
                        continue;
                    }
                    return videos;
                }
            }
        }
        return videos;
    }

}
