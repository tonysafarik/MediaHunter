package facade;

import com.jts.mediahunter.core.service.DatabaseService;
import com.jts.mediahunter.core.service.PluginService;
import com.jts.mediahunter.domain.entities.Channel;
import com.jts.mediahunter.web.WebAdminApplication;
import com.jts.mediahunter.web.WebConfiguration;
import com.jts.mediahunter.web.dto.ChannelInfoDTO;
import com.jts.mediahunter.web.dto.FindChannelDTO;
import com.jts.mediahunter.web.facade.AdministrationFacade;
import com.jts.mediahunter.web.facade.AdministrationFacadeImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.UriComponentsBuilder;
import static org.assertj.core.api.Assertions.*;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author Tony
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebAdminApplication.class)
public class AdministrationFacadeTests {
    
    @Autowired
    private AdministrationFacade admin;
    
    @MockBean
    private DatabaseService db;
    
    @MockBean
    private PluginService plugins;
    
    private final List<Channel> channelDatabase = new ArrayList<>();
    private final List<Channel> channelPlugins = new ArrayList<>();
    
    
    public AdministrationFacadeTests() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    private void createDbChannel(String externalId, String MCP, boolean trusted){
        channelDatabase.add(Channel.builder()
                .externalId(externalId)
                .id(String.valueOf(channelDatabase.size()))
                .nameOfChannel("name of channel " + externalId)
                .nameOfMcp(MCP)
                .trusted(trusted)
                .uri(UriComponentsBuilder.fromPath("https://something.com/" + externalId).build().toUri())
                .build());
        // when in DB, plugin had to produce it
        createPluginChannel(externalId, MCP, trusted);
    }
    
    private void createPluginChannel(String externalId, String MCP, boolean trusted){
        channelPlugins.add(Channel.builder()
                .externalId(externalId)
                .nameOfChannel("name of channel " + externalId)
                .nameOfMcp(MCP)
                .trusted(trusted)
                .uri(UriComponentsBuilder.fromPath("https://something.com/" + externalId).build().toUri())
                .build());
    }
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        createDbChannel("coolstuff", "YouTube", false);
        createDbChannel("trustworthyChannel", "Vimeo", true);
        ServiceMocks.mockDatabaseService(this.db, this.channelDatabase);
        ServiceMocks.mockPluginService(this.plugins, channelPlugins);
    }
    
    @After
    public void tearDown() {
    }
    @Test
    public void getChannelsByExternalIdCorrent(){
        createPluginChannel("coolstuff", "Vimeo", true);
        List<FindChannelDTO> channels = admin.getChannelsByExternalId("coolstuff");
        assertThat(channels).hasSize(2).extracting("externalId").containsOnly("coolstuff");
        assertThat(channels).extracting("mcpName").containsExactlyInAnyOrder("YouTube", "Vimeo");
        assertThat(channels).extracting("internalId").containsExactlyInAnyOrder(null, String.valueOf(0));
    }
    
    @Test
    public void putChannelToDBCorrect(){
        createPluginChannel("myExternalId", "Vimeo", true);
        admin.putChannelToDB("myExternalId", "Vimeo", true);
        assertThat(channelDatabase).filteredOn("externalId", "myExternalId").extracting("id").doesNotContain(null, 0);
    }
    
    @Test
    public void getChannelInfoCorrect(){
        ChannelInfoDTO info = admin.getChannelInfo("1");
        //TODO assert info
    }
    
    @Test
    public void updateChannelCorrect(){
        Channel channel = channelPlugins.get(0);
        channel.setNameOfChannel("newName");
        admin.updateChannel("0");
        assertThat(channelDatabase.get(channelDatabase.size()-1).getNameOfChannel()).isEqualTo("newName");
    }
    
    @Test
    public void deleteChannelCorrect(){
        //TODO test if deleted all channel if should
    }
    
}
