package com.jts.mediahunter.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Tony
 */
@Controller
public class HomeController {
    
    @GetMapping("/")
    public ModelAndView getHomePage(){
        return new ModelAndView("index");
    }
    
}
