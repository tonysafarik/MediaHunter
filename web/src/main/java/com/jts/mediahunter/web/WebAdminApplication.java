package com.jts.mediahunter.web;

import com.jts.mediahunter.core.CoreConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author Tony
 */
@SpringBootApplication(scanBasePackageClasses = {WebConfiguration.class, CoreConfiguration.class})
public class WebAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebAdminApplication.class, args);
    }

}
