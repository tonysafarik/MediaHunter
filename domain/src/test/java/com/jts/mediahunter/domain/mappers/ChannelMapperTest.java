package com.jts.mediahunter.domain.mappers;

import com.jts.mediahunter.domain.DomainConfiguration;
import com.jts.mediahunter.domain.dto.ChannelInfoDTO;
import com.jts.mediahunter.domain.dto.FindChannelDTO;
import com.jts.mediahunter.domain.entities.Channel;
import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {DomainConfiguration.class})
public class ChannelMapperTest {

    @Autowired
    private ChannelMapper mapper;

    @Rule
    public TestRule watcher = new TestWatcher() {
        protected void starting(Description description) {
            log.info("Starting test: " + description.getMethodName());
        }
    };

    private static Channel channel;

    @BeforeClass
    public static void beforeClass(){
        channel = Channel.builder()
                .externalId("externalID")
                .acceptedRecordCount(26L)
                .id("identification")
                .lastRecordUpload(LocalDateTime.now())
                .mcpName("media content provider")
                .name("channel name")
                .recordCount(50L)
                .trusted(false)
                .uri(URI.create("http://www.somesite.org/something"))
                .build();
    }

    @Test
    public void channelToChannelInfoDTO(){
        ChannelInfoDTO info = mapper.channelToChannelInfoDTO(channel);
        assertThat(info).hasNoNullFieldsOrProperties();
    }

    @Test
    public void channelToFindChannelDTO(){
        FindChannelDTO find = mapper.channelToFindChannelDTO(channel);
        assertThat(find).hasNoNullFieldsOrProperties();
    }

}