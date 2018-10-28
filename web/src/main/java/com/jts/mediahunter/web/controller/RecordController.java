package com.jts.mediahunter.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Tony
 */
@Controller
@RequestMapping("/record")
public class RecordController {
    
    @GetMapping("/find")
    public String findChannel(){
        return "/record/find";
    }
    
}
