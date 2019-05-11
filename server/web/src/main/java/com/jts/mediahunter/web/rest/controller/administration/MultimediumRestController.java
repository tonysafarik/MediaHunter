package com.jts.mediahunter.web.rest.controller.administration;

import com.jts.mediahunter.domain.dto.FindRecordDTO;
import com.jts.mediahunter.domain.dto.PostMultimediumDTO;
import com.jts.mediahunter.domain.dto.RecordInfoDTO;
import com.jts.mediahunter.web.facade.AdministrationFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
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
    public List<FindRecordDTO> listRecordsByExternalId(@PathVariable(name="multimediumId") String externalId) {
        List<FindRecordDTO> multimedia = admin.getRecordsByExternalId(externalId);
        return multimedia;
    }

    @PostMapping
    public ResponseEntity putAcceptedRecordToDB(@RequestBody PostMultimediumDTO multimedium) {
        String id = admin.putRecordToDB(multimedium.getExternalId(), multimedium.getMcpName());
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public RecordInfoDTO getRecordInfo(@PathVariable(name = "id") String internalId) {
        RecordInfoDTO info = admin.getRecordInfo(internalId);
        return info;
    }

    @GetMapping("/queue")
    public List<FindRecordDTO> getWaitingRecords(){
        List<FindRecordDTO> records = admin.getWaitingRecords();
        return records;
    }

    //@GetMapping("/update")
    public String updateRecord(@RequestParam(name="id") String internalId, RedirectAttributes redAttr){
        admin.updateRecord(internalId);
        redAttr.addAttribute("id", internalId);
        return "redirect:/record/show";
    }

    @GetMapping("/accept/{id}")
    public ResponseEntity acceptRecord(@PathVariable(name="id") String internalId){
        admin.acceptRecord(internalId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/reject/{id}")
    public ResponseEntity rejectRecord(@PathVariable(name="id") String internalId){
        admin.rejectRecord(internalId);
        return new ResponseEntity(HttpStatus.OK);
    }

}
