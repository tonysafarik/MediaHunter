package com.jts.mediahunter.core.dao;

import com.jts.mediahunter.domain.entities.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *
 * @author Tony
 */
public interface ChannelDAO extends JpaRepository<Channel, String> {
    
    List<Channel> findByExternalId(String channelId);

    List<Channel> findByTrustedIsTrue();
}
