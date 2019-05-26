package com.jts.mediahunter.web.rest.controller.administration;

import com.jts.mediahunter.domain.dto.ChannelInfoDTO;
import com.jts.mediahunter.domain.dto.ChannelPreviewDTO;
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
    public List<ChannelPreviewDTO> listChannelsByExternalId(@PathVariable(name = "channelId") String channelId) {
        List<ChannelPreviewDTO> channels = admin.getChannelsByExternalId(channelId);
        return channels;
    }

    @PostMapping
    public ResponseEntity putChannelToDB(@RequestBody PostChannelDTO channelInfo) {
        log.info("INSERTING CHANNEL/se");
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

    @PatchMapping("/{channelId}")
    public ChannelInfoDTO updateChannel(@PathVariable("channelId") String internalId) {
        admin.updateChannel(internalId);
        ChannelInfoDTO channel = admin.getChannelInfo(internalId);
        return channel;
    }

    @DeleteMapping("/{channelId}")
    public void deleteChannel(@PathVariable("channelId") String internalId) {
        admin.deleteChannel(internalId, true);
    }

}
