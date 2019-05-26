package plugins;

import com.jts.mediahunter.domain.Thumbnail;
import com.jts.mediahunter.domain.entities.Channel;
import com.jts.mediahunter.domain.entities.Multimedium;
import com.jts.mediahunter.plugins.PluginsConfiguration;
import com.jts.mediahunter.plugins.youtube.YouTube;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.junit.*;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
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
@Slf4j
public class YouTubeTests {

    @Autowired
    private YouTube yt;

    @Rule
    public TestRule watcher = new TestWatcher() {
        protected void starting(Description description) {
            log.info("Starting test: " + description.getMethodName());
        }
    };

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
                .name("Tony HBOK crew")
                .mcpName(yt.getMcpName())
                .uri(UriComponentsBuilder.fromUriString("https://www.youtube.com/channel/UCZdunuduJOFxxK0R41o4X-A").build().toUri())
                .build();

        assertThat(channel).isNotNull().isEqualToComparingFieldByField(channelToCompare);
    }

    @Test
    public void getMultimediumByExternalId() {
        Multimedium multimedium = yt.getMultimediumByExternalId("wvJPLXmRrpk");

        Multimedium multimediumToCompare = Multimedium.builder()
                .description("The hard core of HBOK crew, Cajt, is blading almost every day. He started skating about 20 years ago, and he's still killing it!")
                .externalId("wvJPLXmRrpk")
                .mcpName(yt.getMcpName())
                .name("Cajt - HBOK crew - Still Here, Still Blading")
                .thumbnail(new Thumbnail("https://i.ytimg.com/vi/wvJPLXmRrpk/mqdefault.jpg", "https://i.ytimg.com/vi/wvJPLXmRrpk/sddefault.jpg"))
                .uploadTime(LocalDateTime.parse("2016-02-14T18:55:23.000Z", DateTimeFormatter.ISO_ZONED_DATE_TIME))
                .uploaderExternalId("UCZdunuduJOFxxK0R41o4X-A")
                .uri(UriComponentsBuilder.fromUriString("https://www.youtube.com/watch?v=wvJPLXmRrpk").build().toUri())
                .build();

        assertThat(multimedium).isNotNull().isEqualToComparingFieldByField(multimediumToCompare);
    }

    @Test
    public void getAllChannelMultimedia() {
        List<Multimedium> multimedia = yt.getAllChannelMultimedia("UCZdunuduJOFxxK0R41o4X-A");

        assertThat(multimedia).hasSize(45).extracting("name").contains("JEDLA - 2011 - HBOK crew",
                "ONE MINUTE EDIT - Martin \"Santos\" Charvát",
                "Tom - One Trick",
                "Filip Samsonek - HBOK crew - Still Here, Still Blading",
                "Jiří Rygál - HBOK crew - Still Here, Still Blading");
        assertThat(multimedia).extracting("mcpName").containsOnly("YouTube");
        assertThat(multimedia).extracting("uploaderExternalId").containsOnly("UCZdunuduJOFxxK0R41o4X-A");
    }

    @Test
    public void getAllChannelMultimediaOver50Correct() {
        // ID of James Cordens youtube channel. It has over 2900 videos (14.11.2018) 
        List<Multimedium> multimedia = yt.getAllChannelMultimedia("UCJ0uqCI0Vqr2Rrt1HseGirg");
        assertThat(multimedia).size().isGreaterThan(2900);
    }

    @Test
    public void getNewMultimediaCorrect() {
        LocalDateTime time = LocalDateTime.of(2014, Month.JULY, 28, 15, 30);
        List<Multimedium> multimedia = yt.getNewMultimedia("UCZdunuduJOFxxK0R41o4X-A", time);
        List<Multimedium> allChannelMultimedia = yt.getAllChannelMultimedia("UCZdunuduJOFxxK0R41o4X-A");
        assertThat(multimedia).containsAll(allChannelMultimedia.stream().filter(multimedium -> time.isBefore(((Multimedium) multimedium).getUploadTime()) ).collect(Collectors.toList()));
    }

}
