package com.jts.mediahunter.web.rest.controller;

import com.jts.mediahunter.domain.dto.PublicChannelDTO;
import com.jts.mediahunter.domain.dto.PublicRecordDTO;
import com.jts.mediahunter.web.facade.AdministrationFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@Slf4j
@RequestMapping("/api")
public class PublicAPI {

    @Autowired
    private AdministrationFacade admin;

    @GetMapping("/records/{page}")
    public List<PublicRecordDTO> pageOfRecords(@PathVariable("page") int page){
        List<PublicRecordDTO> records = admin.getRecordsPage(page);
        log.info(records.toString());
        return records;

    }

    @GetMapping("/channels")
    public List<PublicChannelDTO> listAllTrustedChannels() {
        log.info("called");
        return admin.getTrustedChannels();
    }

}
