package com.jts.mediahunter.domain.mappers;

import com.jts.mediahunter.domain.DataModelConfiguration;
import com.jts.mediahunter.domain.RecordStage;
import com.jts.mediahunter.domain.Thumbnail;
import com.jts.mediahunter.domain.dto.MultimediumInfoDTO;
import com.jts.mediahunter.domain.dto.MultimediumPreviewDTO;
import com.jts.mediahunter.domain.dto.PublicMultimediumDTO;
import com.jts.mediahunter.domain.entities.Multimedium;
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
@ContextConfiguration(classes = {DataModelConfiguration.class})
public class MultimediumMapperTest {

    @Autowired
    private MultimediumMapper mapper;

    @Rule
    public TestRule watcher = new TestWatcher() {
        protected void starting(Description description) {
            log.info("Starting test: " + description.getMethodName());
        }
    };

    private static Multimedium multimedium;

    @BeforeClass
    public static void beforeClass() {
        multimedium = Multimedium.builder()
                .description("")
                .externalId("")
                .id("")
                .mcpName("")
                .name("")
                .stage(RecordStage.ACCEPTED)
                .thumbnail(
                        new Thumbnail("http://lower.org", "http://higher.org"))
                .uploaderExternalId("")
                .uploadTime(LocalDateTime.now())
                .uri(URI.create("http://somesite.org"))
                .build();
    }

    @Test
    public void multimediumToMultimediumPreviewDTO(){
        MultimediumPreviewDTO find = mapper.multimediumToMultimediumPreviewDTO(multimedium);
        assertThat(find).hasNoNullFieldsOrProperties();
    }

    @Test
    public void multimediumToMultimediumInfoDTO(){
        MultimediumInfoDTO info = mapper.multimediumToMultimediumInfoDTO(multimedium);
        assertThat(info).hasNoNullFieldsOrProperties();
    }

    @Test
    public void multimediumToPublicMultimediumDTO(){
        PublicMultimediumDTO pub = mapper.multimediumToPublicMultimediumDTO(multimedium);
        assertThat(pub).hasNoNullFieldsOrProperties();
    }

}