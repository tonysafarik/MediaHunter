package com.jts.mediahunter.core.dao;

import com.jts.mediahunter.domain.entities.Multimedium;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 *
 * @author Tony
 */
public interface MultimediumDAO extends MongoRepository<Multimedium, String> {

    public List<Multimedium> findByExternalId(String externalId);
    
    @Query(value = "{ 'uploaderExternalId' : ?0, 'mcpName' : ?1 }")
    public List<Multimedium> findByUploader(String uploaderExternalId, String nameOfMcp);
    
    @Query(value = "{ 'stage' : 'WAITING' }")
    public List<Multimedium> findWaitingMultimedia();

    @Query(value = "{ 'stage' : 'ACCEPTED' }")
    public Page<Multimedium> findAcceptedMultimedia(Pageable pageable);

    public List<Multimedium> findByUploaderExternalId(String uploaderExternalId);

}
