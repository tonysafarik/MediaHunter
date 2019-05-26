package com.jts.mediahunter.web;

import com.jts.mediahunter.core.DatabaseConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author Tony
 */
@SpringBootApplication(scanBasePackageClasses = {WebConfiguration.class, DatabaseConfiguration.class})
public class WebAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebAdminApplication.class, args);
    }

}
