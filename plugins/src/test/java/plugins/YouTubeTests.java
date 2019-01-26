package plugins;

import com.jts.mediahunter.domain.Thumbnail;
import com.jts.mediahunter.domain.entities.Channel;
import com.jts.mediahunter.domain.entities.Record;
import com.jts.mediahunter.plugins.PluginsConfiguration;
import com.jts.mediahunter.plugins.youtube.YouTube;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.*;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author Tony
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {PluginsConfiguration.class})
public class YouTubeTests {

    @Autowired
    private YouTube yt;

    public YouTubeTests() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getChannelByExternalIdCorrect() {
        Channel channel = yt.getChannelByExternalId("UCZdunuduJOFxxK0R41o4X-A");

        Channel channelToCompare = Channel.builder()
                .externalId("UCZdunuduJOFxxK0R41o4X-A")
                .nameOfChannel("Tony HBOK crew")
                .nameOfMcp(yt.getMcpName())
                .uri(UriComponentsBuilder.fromUriString("https://www.youtube.com/channel/UCZdunuduJOFxxK0R41o4X-A").build().toUri())
                .build();

        assertThat(channel).isNotNull().isEqualToComparingFieldByField(channelToCompare);
    }

    @Test
    public void getRecordByExternalIdCorrect() {
        Record record = yt.getRecordByExternalId("wvJPLXmRrpk");

        Record recordToCompare = Record.builder()
                .description("The hard core of HBOK crew, Cajt, is blading almost every day. He started skating about 20 years ago, and he's still killing it!")
                .externalId("wvJPLXmRrpk")
                .nameOfMcp(yt.getMcpName())
                .nameOfRecord("Cajt - HBOK crew - Still Here, Still Blading")
                .thumbnail(new Thumbnail("https://i.ytimg.com/vi/wvJPLXmRrpk/mqdefault.jpg", "https://i.ytimg.com/vi/wvJPLXmRrpk/sddefault.jpg"))
                .uploadTime(LocalDateTime.parse("2016-02-14T18:55:23.000Z", DateTimeFormatter.ISO_ZONED_DATE_TIME))
                .uploaderExternalId("UCZdunuduJOFxxK0R41o4X-A")
                .uri(UriComponentsBuilder.fromUriString("https://www.youtube.com/watch?v=wvJPLXmRrpk").build().toUri())
                .build();

        assertThat(record).isNotNull().isEqualToComparingFieldByField(recordToCompare);
    }

    @Test
    public void getAllChannelRecordsCorrect() {
        List<Record> records = yt.getAllChannelRecords("UCZdunuduJOFxxK0R41o4X-A");

        assertThat(records).hasSize(41).extracting("nameOfRecord").contains("JEDLA - 2011 - HBOK crew",
                "ONE MINUTE EDIT - Martin \"Santos\" Charvát",
                "Tom - One Trick",
                "Filip Samsonek - HBOK crew - Still Here, Still Blading",
                "Jiří Rygál - HBOK crew - Still Here, Still Blading");
        assertThat(records).extracting("nameOfMcp").containsOnly("YouTube");
        assertThat(records).extracting("uploaderExternalId").containsOnly("UCZdunuduJOFxxK0R41o4X-A");
    }

    @Test
    public void getAllChannelRecordsOver50Correct() {
        // ID of James Cordens youtube channel. It has over 2900 videos (14.11.2018) 
        List<Record> records = yt.getAllChannelRecords("UCJ0uqCI0Vqr2Rrt1HseGirg");
        assertThat(records).size().isGreaterThan(2900);
    }

    @Test
    public void getNewRecordsCorrect() {
        LocalDateTime time = LocalDateTime.of(2014, Month.JULY, 28, 15, 30);
        List<Record> records = yt.getNewRecords("UCZdunuduJOFxxK0R41o4X-A", time);
        List<Record> allChannelRecords = yt.getAllChannelRecords("UCZdunuduJOFxxK0R41o4X-A");
        assertThat(records).containsAll(allChannelRecords.stream().filter( record -> time.isBefore(((Record) record).getUploadTime()) ).collect(Collectors.toList()));
    }

}
