package com.jts.mediahunter.web.rest.controller;

import com.jts.mediahunter.domain.dto.PublicRecordDTO;
import com.jts.mediahunter.web.facade.AdministrationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RecordRestController {

    @Autowired
    private AdministrationFacade admin;

    @GetMapping("/records({page})")
    public List<PublicRecordDTO> pageOfRecords(@PathVariable("page") int page){

        return admin.getRecordsPage(page);

    }

}
