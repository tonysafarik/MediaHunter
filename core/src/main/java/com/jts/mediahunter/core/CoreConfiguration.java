package com.jts.mediahunter.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import com.jts.mediahunter.domain.DomainConfiguration;
import com.jts.mediahunter.plugins.PluginsConfiguration;
import org.springframework.context.annotation.PropertySource;

/**
 *
 * @author Jan Tony Safarik
 */
@Configuration
@ComponentScan(basePackageClasses = {DomainConfiguration.class, PluginsConfiguration.class})
@PropertySource({"classpath:core.properties", "classpath:secret.properties"})
public class CoreConfiguration {
    
}
