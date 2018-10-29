package dao;

import com.jts.mediahunter.core.CoreConfiguration;
import com.jts.mediahunter.core.dao.ChannelDAO;
import com.jts.mediahunter.domain.entities.Channel;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;
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
@ContextConfiguration(classes = {CoreConfiguration.class})
public class ChannelDAOTests {

    @Autowired
    private ChannelDAO channelDAO;
    
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
                .nameOfChannel("Name of channel")
                .nameOfMcp("Name of media content provider")
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
