package dao;

import static org.assertj.core.api.Assertions.*;
import com.jts.mediahunter.core.DatabaseConfiguration;
import com.jts.mediahunter.core.dao.ChannelDAO;
import com.jts.mediahunter.domain.entities.Channel;
import java.util.List;
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
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author Tony
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {DatabaseConfiguration.class})
@Slf4j
public class ChannelDAOTests {

    @Autowired
    private ChannelDAO channelDAO;

    @Rule
    public TestRule watcher = new TestWatcher() {
        protected void starting(Description description) {
            log.info("Starting test: " + description.getMethodName());
        }
    };

    public ChannelDAOTests() {
    }

    private Channel sample1;
    
    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.sample1 = Channel.builder()
                .externalId("externalID")
                .name("Name of channel")
                .mcpName("Name of media content provider")
                .uri(UriComponentsBuilder.fromUriString("https://www.youtube.com/somesome?donthaveto=makesense").build().toUri())
                .build();
    }

    @After
    public void tearDown() {
        if (channelDAO.findById(sample1.getId()).isPresent()) {
            channelDAO.delete(sample1);
        }
    }

    /**
     * Because channelDAO findByExternalId is generetated by Spring and the name
     * of the attribute can be changed, it is tested to ensure correctness
     */
    @Test
    public void testFindByExternalId() {
        sample1 = channelDAO.insert(this.sample1);
        
        List<Channel> findChannel = channelDAO.findByExternalId(sample1.getExternalId());
        assertThat(findChannel).containsExactly(sample1);
        
        channelDAO.delete(sample1);
        
    }

}
