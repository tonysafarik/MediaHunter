package com.jts.mediahunter.core;

import com.jts.mediahunter.core.dao.ChannelDAO;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import com.jts.mediahunter.domain.DomainConfiguration;
import com.jts.mediahunter.plugins.PluginsConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 *
 * @author Jan Tony Safarik
 */
@Configuration
@PropertySource({"classpath:core.properties", "classpath:secret.properties"})
@EnableAutoConfiguration
@ComponentScan(basePackageClasses = {DomainConfiguration.class, PluginsConfiguration.class})
public class CoreConfiguration {
    
}
