package com.jts.mediahunter.plugins;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 *
 * @author Tony
 */
@Configuration
@PropertySource({"classpath:secret.properties"})
public class SecretConfiguration {
    
}
