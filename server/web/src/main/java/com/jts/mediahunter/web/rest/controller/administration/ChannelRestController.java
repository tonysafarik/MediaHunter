package com.jts.mediahunter.web.rest.controller.administration;

import com.jts.mediahunter.domain.dto.ChannelInfoDTO;
import com.jts.mediahunter.domain.dto.FindChannelDTO;
import com.jts.mediahunter.domain.dto.PostChannelDTO;
import com.jts.mediahunter.web.facade.AdministrationFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/channel")
@Slf4j
public class ChannelRestController {

    @Autowired
    private AdministrationFacade admin;

    @GetMapping("/search/{channelId}")
    public List<FindChannelDTO> listChannelsByExternalId(@PathVariable(name = "channelId") String channelId) {
        List<FindChannelDTO> channels = admin.getChannelsByExternalId(channelId);
        return channels;
    }

    @PostMapping
    public ResponseEntity putChannelToDB(@RequestBody PostChannelDTO channelInfo) {
        ChannelInfoDTO channel = admin.putChannelToDB(channelInfo.getExternalId(), channelInfo.getMcpName(), channelInfo.isTrusted());
        if (channel == null) {
            return new ResponseEntity(HttpStatus.PROCESSING);
        }
        return new ResponseEntity<>(channel.getId(),HttpStatus.CREATED);
    }

    @GetMapping("/{channelId}")
    public ChannelInfoDTO getChannelInfo(@PathVariable(name = "channelId") String internalId) {
        ChannelInfoDTO channel = admin.getChannelInfo(internalId);
        return channel;
    }

    //@GetMapping("/update/{channelId}")
    public ChannelInfoDTO updateChannel(@PathVariable String internalId) {
        admin.updateChannel(internalId);
        // TODO
        return null;
    }

    //@DeleteMapping("/delete/{channelId}")
    public void deleteChannel(@PathVariable String internalId) {
        // TODO
        admin.deleteChannel(internalId, true);
    }

}
