package com.jts.mediahunter.core.dao;

import com.jts.mediahunter.core.dao.recordstages.AcceptedStage;
import com.jts.mediahunter.core.dao.recordstages.RejectedStage;
import com.jts.mediahunter.domain.entities.Record;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Tony
 */
public interface RecordDAO extends MongoRepository<Record, String>, AcceptedStage, RejectedStage {
    
    /**
     * finds Records ONLY in waiting stage!
     * @param externalId
     * @return 
     */
    public List<Record> findByExternalId(String externalId);
    
}
