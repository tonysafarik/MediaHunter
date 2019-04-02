package com.jts.mediahunter.domain.mappers;

import com.jts.mediahunter.domain.DomainConfiguration;
import com.jts.mediahunter.domain.RecordStage;
import com.jts.mediahunter.domain.Thumbnail;
import com.jts.mediahunter.domain.dto.FindRecordDTO;
import com.jts.mediahunter.domain.dto.PublicRecordDTO;
import com.jts.mediahunter.domain.dto.RecordInfoDTO;
import com.jts.mediahunter.domain.entities.Record;
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
public class RecordMapperTest {

    @Autowired
    private RecordMapper mapper;

    @Rule
    public TestRule watcher = new TestWatcher() {
        protected void starting(Description description) {
            log.info("Starting test: " + description.getMethodName());
        }
    };

    private static Record record;

    @BeforeClass
    public static void beforeClass() {
        record = Record.builder()
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
    public void recordToFindRecordDTO(){
        FindRecordDTO find = mapper.recordToFindRecordDTO(record);
        assertThat(find).hasNoNullFieldsOrProperties();
    }

    @Test
    public void recordToRecordInfoDTO(){
        RecordInfoDTO info = mapper.recordToRecordInfoDTO(record);
        assertThat(info).hasNoNullFieldsOrProperties();
    }

    @Test
    public void recordToPublicRecordDTO(){
        PublicRecordDTO pub = mapper.recordToPublicRecordDTO(record);
        assertThat(pub).hasNoNullFieldsOrProperties();
    }

}