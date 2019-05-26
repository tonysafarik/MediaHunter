package com.jts.mediahunter.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import com.jts.mediahunter.domain.DataModelConfiguration;
import com.jts.mediahunter.plugins.PluginsConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.PropertySource;

/**
 *
 * @author Jan Tony Safarik
 */
@Configuration
@PropertySource({"classpath:database.properties"})
@EnableAutoConfiguration
@ComponentScan(basePackageClasses = {DataModelConfiguration.class, PluginsConfiguration.class})
public class DatabaseConfiguration {
    
}
