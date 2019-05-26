package com.jts.mediahunter.web.rest.controller;

import com.jts.mediahunter.domain.dto.PublicChannelDTO;
import com.jts.mediahunter.domain.dto.PublicMultimediumDTO;
import com.jts.mediahunter.web.facade.AdministrationFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@Slf4j
@RequestMapping("/api")
public class PublicAPI {

    @Autowired
    private AdministrationFacade admin;

    @GetMapping("/records/{page}")
    public List<PublicMultimediumDTO> pageOfMultimedia(@PathVariable("page") int page){
        List<PublicMultimediumDTO> multimedia = admin.getMultimediaPage(page);
        return multimedia;

    }

    @GetMapping("/channels")
    public List<PublicChannelDTO> listAllTrustedChannels() {
        return admin.getTrustedChannels();
    }

}
