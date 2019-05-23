package com.jts.mediahunter.core.dao;

import com.jts.mediahunter.domain.entities.Record;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 *
 * @author Tony
 */
public interface RecordDAO extends MongoRepository<Record, String> {

    public List<Record> findByExternalId(String externalId);
    
    @Query(value = "{ 'uploaderExternalId' : ?0, 'mcpName' : ?1 }")
    public List<Record> findByUploader(String uploaderExternalId, String nameOfMcp);
    
    @Query(value = "{ 'stage' : 'WAITING' }")
    public List<Record> findWaitingRecords();

    @Query(value = "{ 'stage' : 'ACCEPTED' }")
    public Page<Record> findAcceptedRecords(Pageable pageable);

    public List<Record> findByUploaderExternalId(String uploaderExternalId);

}
