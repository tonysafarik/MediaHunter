package com.jts.mediahunter.web.rest.controller.administration;

import com.jts.mediahunter.domain.dto.MultimediumPreviewDTO;
import com.jts.mediahunter.domain.dto.PostMultimediumDTO;
import com.jts.mediahunter.domain.dto.MultimediumInfoDTO;
import com.jts.mediahunter.web.facade.AdministrationFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/multimedium")
@Slf4j
public class MultimediumRestController {

    @Autowired
    private AdministrationFacade admin;

    @GetMapping("/search/{multimediumId}")
    public List<MultimediumPreviewDTO> listMultimediaByExternalId(@PathVariable(name="multimediumId") String externalId) {
        List<MultimediumPreviewDTO> multimedia = admin.getMultimediaByExternalId(externalId);
        return multimedia;
    }

    @PostMapping
    public ResponseEntity putAcceptedMultimediumToDB(@RequestBody PostMultimediumDTO multimedium) {
        String id = admin.putMultimediumToDB(multimedium.getExternalId(), multimedium.getMcpName());
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public MultimediumInfoDTO getMultimediumInfo(@PathVariable(name = "id") String internalId) {
        MultimediumInfoDTO info = admin.getMultimediumInfo(internalId);
        return info;
    }

    @GetMapping("/queue")
    public List<MultimediumPreviewDTO> getWaitingMultimedia(){
        List<MultimediumPreviewDTO> multimedia = admin.getWaitingMultimedia();
        return multimedia;
    }

    @PatchMapping("/{multimediumId}")
    public MultimediumInfoDTO updateMultimedium(@PathVariable(name="multimediumId") String internalId){
        admin.updateMultimedium(internalId);
        return admin.getMultimediumInfo(internalId);
    }

    @GetMapping("/accept/{id}")
    public ResponseEntity acceptMultimedium(@PathVariable(name="id") String internalId){
        admin.acceptMultimedium(internalId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/reject/{id}")
    public ResponseEntity rejectMultimedium(@PathVariable(name="id") String internalId){
        admin.rejectMultimedium(internalId);
        return new ResponseEntity(HttpStatus.OK);
    }

}
