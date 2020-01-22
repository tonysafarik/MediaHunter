package com.jts.mediahunter.core.dao;

import com.jts.mediahunter.domain.entities.Multimedium;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MultimediumDAO extends JpaRepository<Multimedium, String> {

    List<Multimedium> findByExternalId(String externalId);

//    @Query(value = "SELECT m FROM multimedium m WHERE m.uploader_external_id = ?0 AND m.mcp_name = ?1")
//    List<Multimedium> findByUploader(String uploaderExternalId, String nameOfMcp);
//
//    @Query(value = "SELECT m FROM multimedium m WHERE m.stage = com.jts.mediahunter.domain.RecordStage.WAITING")
//    List<Multimedium> findWaitingMultimedia();
//
//    @Query(value = "SELECT m FROM multimedium m WHERE m.stage = com.jts.mediahunter.domain.RecordStage.ACCEPTED")
//    Page<Multimedium> findAcceptedMultimedia(Pageable pageable);

    List<Multimedium> findByUploaderExternalId(String uploaderExternalId);

}
