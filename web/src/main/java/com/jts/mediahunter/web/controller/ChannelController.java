package com.jts.mediahunter.web.controller;

import com.jts.mediahunter.web.dto.ChannelInfoDTO;
import com.jts.mediahunter.web.dto.FindChannelDTO;
import com.jts.mediahunter.web.facade.AdministrationFacade;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Tony
 */
@Controller
@RequestMapping("/channel")
public class ChannelController {

    @Autowired
    private AdministrationFacade admin;

    @GetMapping("/find")
    public String findChannel() {
        return "/channel/find";
    }

    @GetMapping("/list")
    public ModelAndView listChannelsByExternalId(@RequestParam(name = "channelId") String channelId) {
        ModelAndView mnv = new ModelAndView("/channel/list");
        List<FindChannelDTO> channels = admin.getChannelsByExternalId(channelId);
        mnv.addObject("channels", channels);
        return mnv;
    }

    @PostMapping("/put")
    public String putChannelToDB(@RequestParam(name = "externalId") String externalId, @RequestParam(name = "mcpName") String mcpName, @RequestParam(name = "trusted", required = false) boolean trusted, RedirectAttributes redAttr) {
        String id = admin.putChannelToDB(externalId, mcpName, trusted);
        redAttr.addAttribute("id", id);
        return "redirect:/channel/show";
    }

    @GetMapping("/show")
    public ModelAndView getChannelInfo(@RequestParam(name = "id") String internalId) {
        ChannelInfoDTO channel = admin.getChannelInfo(internalId);
        return new ModelAndView("/channel/show", "channel", channel);
    }

    @PostMapping("/update")
    public String updateChannel(@RequestParam String internalId, RedirectAttributes redAttr) {
        admin.updateChannel(internalId);
        redAttr.addAttribute("id", internalId);
        return "redirect:/channel/show";
    }

    @DeleteMapping("/delete")
    public String deleteChannel(@RequestParam String internalId, @RequestParam boolean deleteRecords) {
        admin.deleteChannel(internalId, deleteRecords);
        return "";
    }

}
