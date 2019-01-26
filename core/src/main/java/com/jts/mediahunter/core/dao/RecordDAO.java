package com.jts.mediahunter.core.dao;

import com.jts.mediahunter.domain.entities.Record;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 *
 * @author Tony
 */
public interface RecordDAO extends MongoRepository<Record, String> {

    public List<Record> findByExternalId(String externalId);
    
    @Query(value = "{ 'uploaderExternalId' : ?0, 'nameOfMcp' : ?1 }")
    public List<Record> findByUploader(String uploaderExternalId, String nameOfMcp);
    
    @Query(value = "{ 'stage' : 'WAITING' }")
    public List<Record> findWaitingRecords();
    
}
