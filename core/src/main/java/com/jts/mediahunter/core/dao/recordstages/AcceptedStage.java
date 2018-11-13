package com.jts.mediahunter.core.dao.recordstages;

import com.jts.mediahunter.domain.entities.Record;

/**
 *
 * @author Tony
 */
public interface AcceptedStage {
    
    public void acceptWaitingRecord(Record record);
    
    public void acceptRejectedRecord(Record record);
    
}
