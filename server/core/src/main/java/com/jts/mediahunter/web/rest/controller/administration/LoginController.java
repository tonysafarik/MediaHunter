package com.jts.mediahunter.web.rest.controller.administration;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping
public class LoginController {

    @GetMapping("/login")
    public void handleError() {
    }

}
