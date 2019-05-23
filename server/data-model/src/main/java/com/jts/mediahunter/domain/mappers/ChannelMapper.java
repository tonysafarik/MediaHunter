package com.jts.mediahunter.domain.mappers;

import com.jts.mediahunter.domain.dto.ChannelInfoDTO;
import com.jts.mediahunter.domain.dto.FindChannelDTO;
import com.jts.mediahunter.domain.dto.PublicChannelDTO;
import com.jts.mediahunter.domain.entities.Channel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChannelMapper {

    ChannelInfoDTO channelToChannelInfoDTO(Channel channel);

    FindChannelDTO channelToFindChannelDTO(Channel channel);

    PublicChannelDTO channelToPublicChannelDTO(Channel channel);

}
