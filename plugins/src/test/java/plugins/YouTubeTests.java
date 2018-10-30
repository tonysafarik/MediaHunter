package plugins;

import com.jts.mediahunter.domain.Thumbnail;
import com.jts.mediahunter.domain.entities.Channel;
import com.jts.mediahunter.domain.entities.Record;
import com.jts.mediahunter.plugins.PluginsConfiguration;
import com.jts.mediahunter.plugins.youtube.YouTube;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
    public void testFindChannelById() {
        List<Channel> channel = yt.getChannelByExternalId("UCZdunuduJOFxxK0R41o4X-A");

        Channel channelToCompare = Channel.builder()
                .externalId("UCZdunuduJOFxxK0R41o4X-A")
                .nameOfChannel("Tony HBOK crew")
                .nameOfMcp(yt.getMcpName())
                .uri(UriComponentsBuilder.fromUriString("https://www.youtube.com/channel/UCZdunuduJOFxxK0R41o4X-A").build().toUri())
                .build();

        assertThat(channel.get(0)).isNotNull().isEqualToComparingFieldByField(channelToCompare);
    }

    @Test
    public void testFindRecordById() {
        List<Record> record = yt.getRecordByExternalId("wvJPLXmRrpk");
        
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

        assertThat(record.get(0)).isNotNull().isEqualToComparingFieldByField(recordToCompare);
    }

}
