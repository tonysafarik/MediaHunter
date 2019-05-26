package facade;

import com.jts.mediahunter.core.service.DatabaseService;
import com.jts.mediahunter.plugins.service.PluginService;
import com.jts.mediahunter.domain.entities.Channel;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doAnswer;

/**
 *
 * @author Tony
 */
public class ServiceMocks {

    /**
     * mocks all database service methods for proper testing of higher layers
     * @param db
     * @param channels 
     */
    public static void mockDatabaseService(DatabaseService db, List<Channel> channels){
        when(db.getChannelsByExternalId(any(String.class))).then((invocation) -> {
            List<Channel> channelsToReturn = new ArrayList<>();
            String externalId = invocation.getArgument(0);
            for (Channel channel : channels) {
                if (channel.getExternalId().equals(externalId)) {
                    channelsToReturn.add(channel);
                }
            }
            return channelsToReturn;
        });
        
        when(db.putChannelToDB(any(Channel.class))).then((invocation) -> {
            Channel channel = invocation.getArgument(0);
            String id = String.valueOf(channels.size());
            Channel newChannel = Channel.builder()
                    .externalId(channel.getExternalId())
                    .id(id)
                    .name(channel.getName())
                    .mcpName(channel.getMcpName())
                    .trusted(channel.isTrusted())
                    .uri(channel.getUri())
                    .build();
            channels.add(newChannel);
            return id;
        });
        
        when(db.getChannelById(any(String.class))).then((invocation) -> {
            for (Channel channel : channels) {
                if (channel.getId().equals(invocation.getArgument(0))) {
                    return channel;
                }
            }
            return null;
        });
        
        doAnswer(invocation -> {
            Channel channel = invocation.getArgument(0);
            for (int i = 0; i < channels.size(); i++) {
                if (channels.get(i).getId().equals(channel.getId())) {
                    channels.remove(channels.get(i));
                    channels.add(channel);
                }
            }
            return null;
        }).when(db).updateChannel(any(Channel.class));
    }
    
    public static void mockPluginService(PluginService plugins, List<Channel> channels){
        when(plugins.getChannelsByExternalId(any(String.class))).then((invocation) -> {
            List<Channel> channelsToReturn = new ArrayList<>();
            String externalId = invocation.getArgument(0);
            for (Channel channel : channels) {
                if (channel.getExternalId().equals(externalId)) {
                    channelsToReturn.add(channel);
                }
            }
            return channelsToReturn;
        });
        
        when(plugins.getChannelByExternalId(any(String.class), any(String.class))).then((invocation) -> {
            String externalId = invocation.getArgument(0);
            String nameOfMCP = invocation.getArgument(1);
            for (Channel channel : channels) {
                if (channel.getExternalId().equals(externalId) && channel.getMcpName().equals(nameOfMCP)) {
                    return channel;
                }
            }
            return null;
        });
        
    }
    
}
