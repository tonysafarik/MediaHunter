package com.jts.mediahunter.web.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Tony
 */
//@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping("/error")
    public ModelAndView handleError(HttpServletRequest request){
        ModelAndView mnw = new ModelAndView("/error/errorMessageHolder");
        switch (getErrorCode(request)) {
            case 404:
                mnw.addObject("errorMessage", "404 - Page not found!");
                break;
            default:
                mnw.addObject("errorMessage", "An error occurred");
                break;
        }
        return mnw;
    }
    
    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest
          .getAttribute("javax.servlet.error.status_code");
    }
    
    @Override
    public String getErrorPath() {
        return "/error";
    }
    
}
