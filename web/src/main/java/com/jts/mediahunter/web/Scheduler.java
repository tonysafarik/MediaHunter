package com.jts.mediahunter.web;

import com.jts.mediahunter.web.facade.AdministrationFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Scheduler {

    @Autowired
    private AdministrationFacade admin;

    @Scheduled(fixedDelay = 90000, initialDelay = 120000)
    private void checkForNewRecords(){
        log.info("running scheduled task to find all new media");
        admin.addAllNewMedia();
    }

}
