package com.jts.mediahunter.plugins.vimeo;

import com.jts.mediahunter.domain.entities.Channel;
import com.jts.mediahunter.domain.entities.Record;
import com.jts.mediahunter.plugins.MediaContentProviderPlugin;
import com.jts.mediahunter.plugins.vimeo.dto.VimeoUser;
import com.jts.mediahunter.plugins.vimeo.dto.VimeoVideo;
import com.jts.mediahunter.plugins.vimeo.dto.VimeoVideoList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class Vimeo implements MediaContentProviderPlugin {

    private final String SERVICE_NAME = "Vimeo";

    @Autowired
    private RestTemplate rest;

    @Value("${vimeo.apikey}")
    private String API_KEY;

    private String API_URI = "https://api.vimeo.com";

    private HttpHeaders headers;

    @PostConstruct
    public void setHeaders() {
        headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.API_KEY);
    }

    @Override
    public Channel getChannelByExternalId(String channelId) {

        String uri = API_URI + "/users/" + channelId;
        VimeoUser user = null;
        try {
            user = rest.exchange(uri, HttpMethod.GET, new HttpEntity<>("parameters", headers), VimeoUser.class).getBody();
        } catch (RestClientException e) {
            log.error("Channel on Vimeo with ID " + channelId + " doesn't exist");
        }
        if (user != null) {
            return Channel.builder()
                    .externalId(channelId)
                    .name(user.getName())
                    .mcpName(this.SERVICE_NAME)
                    .uri(user.getUri())
                    .build();
        }
        return null;
    }

    @Override
    public Record getRecordByExternalId(String recordId) {
        String uri = API_URI + "/videos/" + recordId;
        VimeoVideo video = null;
        try {
            video = rest.exchange(uri, HttpMethod.GET, new HttpEntity<>("parameters", headers), VimeoVideo.class).getBody();
        } catch (RestClientException e) {
            log.error("Multimedium on Vimeo with ID " + recordId + " doesn't exist");
        }

        if (video != null) {
            return Record.builder()
                    .description(video.getDescription())
                    .externalId(video.getExternalId())
                    .mcpName(this.SERVICE_NAME)
                    .name(video.getName())
                    .thumbnail(video.getThumbnail())
                    .uploadTime(video.getUploadTime())
                    .uploaderExternalId(video.getUploaderExternalId())
                    .uri(video.getUri())
                    .build();
        }
        return null;
    }

    @Override
    public String getMcpName() {
        return this.SERVICE_NAME;
    }

    @Override
    public List<Record> getAllChannelRecords(String channelId) {
        return getNewRecords(channelId, LocalDateTime.MIN);
    }

    @Override
    public List<Record> getNewRecords(String channelId, LocalDateTime oldestRecord) {
        List<Record> records = new ArrayList<>();

        String uri = API_URI + "/users/" + channelId + "/videos";
        VimeoVideoList list = null;

        uri = UriComponentsBuilder.fromUriString(uri)
                .queryParam("direction", "desc")
                .queryParam("page", 1)
                .build()
                .toString();

        do {
            try {
                log.info(uri);
                list = rest.exchange(
                        uri,
                        HttpMethod.GET,
                        new HttpEntity<>("parameters", headers),
                        VimeoVideoList.class)
                        .getBody();
            } catch (RestClientException e) {
                log.error("Something went wrong when getting videos from channel " + channelId, e);
                continue;
            }

            if (list != null) {
                for (VimeoVideo video : list.getVideos()) {
                    log.info(video.getName() + " " + video.getUploadTime().toString());
                    log.info("oldest record " + oldestRecord.toString());
                    log.info(String.valueOf(video.getUploadTime().toEpochSecond(ZoneOffset.UTC) - oldestRecord.toEpochSecond(ZoneOffset.UTC)));
                    if (video.getUploadTime().isAfter(oldestRecord)) {
                        records.add(Record.builder()
                                .description(video.getDescription())
                                .externalId(video.getExternalId())
                                .mcpName(this.SERVICE_NAME)
                                .name(video.getName())
                                .thumbnail(video.getThumbnail())
                                .uploadTime(video.getUploadTime())
                                .uploaderExternalId(video.getUploaderExternalId())
                                .uri(video.getUri())
                                .build());
                    } else {
                        list.setNextPage(null);
                    }

                }
                uri = API_URI + list.getNextPage();
            }
        } while (list != null && list.getNextPage() != null);
        return records;
    }
}
