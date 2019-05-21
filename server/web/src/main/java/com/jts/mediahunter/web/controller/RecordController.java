package com.jts.mediahunter.web.controller;

import com.jts.mediahunter.domain.dto.MultimediumPreviewDTO;
import com.jts.mediahunter.domain.dto.RecordInfoDTO;
import com.jts.mediahunter.web.facade.AdministrationFacade;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Tony
 */
//@Controller
//@RequestMapping("/record")
public class RecordController {

    @Autowired
    private AdministrationFacade admin;

    @GetMapping("/find")
    public String findChannel() {
        return "/record/find";
    }

    @GetMapping("/list")
    public ModelAndView listRecordsByExternalId(@RequestParam(name = "externalId") String externalId) {
        ModelAndView mnv = new ModelAndView("/record/list");
        List<MultimediumPreviewDTO> records = admin.getRecordsByExternalId(externalId);
        mnv.addObject("records", records);
        mnv.addObject("selectedRecord", records);
        return mnv;
    }

    @PostMapping("/put")
    public String putAcceptedRecordToDB(@RequestParam(name="externalId") String externalId, @RequestParam(name="mcpName") String mcpName, RedirectAttributes redAttr) {
        String id = admin.putRecordToDB(externalId, mcpName);
        redAttr.addAttribute("id", id);
        return "redirect:/record/show";
    }

    @GetMapping("/show")
    public ModelAndView getRecordInfo(@RequestParam(name = "id") String internalId) {
        RecordInfoDTO info = admin.getRecordInfo(internalId);
        return new ModelAndView("/record/show", "record", info);
    }

    @GetMapping("/queue")
    public ModelAndView getWaitingRecords(){
        ModelAndView mnv = new ModelAndView("/record/queue");
        List<MultimediumPreviewDTO> records = admin.getWaitingRecords();
        mnv.addObject("records", records);
        return mnv;
    }
    
    @GetMapping("/update")
    public String updateRecord(@RequestParam(name="id") String internalId, RedirectAttributes redAttr){
        admin.updateRecord(internalId);
        redAttr.addAttribute("id", internalId);
        return "redirect:/record/show";
    }
    
    @GetMapping("/accept")
    public String acceptRecord(@RequestParam(name="id") String internalId, RedirectAttributes redAttr){
        admin.acceptRecord(internalId);
        redAttr.addAttribute("id", internalId);
        return "redirect:/record/show";
    }
    
    @GetMapping("/reject")
    public String rejectRecord(@RequestParam(name="id") String internalId, RedirectAttributes redAttr){
        admin.rejectRecord(internalId);
        redAttr.addAttribute("id", internalId);
        return "redirect:/record/show";
    }
    
}
