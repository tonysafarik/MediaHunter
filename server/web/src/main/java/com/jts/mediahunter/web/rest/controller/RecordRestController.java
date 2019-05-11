package com.jts.mediahunter.web.rest.controller;

import com.jts.mediahunter.domain.dto.PublicRecordDTO;
import com.jts.mediahunter.web.facade.AdministrationFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@Slf4j
@RequestMapping("/api")
public class RecordRestController {

    @Autowired
    private AdministrationFacade admin;

    @GetMapping("/records/{page}")
    public List<PublicRecordDTO> pageOfRecords(@PathVariable("page") int page){
        log.info("called");
        return admin.getRecordsPage(page);

    }

}
