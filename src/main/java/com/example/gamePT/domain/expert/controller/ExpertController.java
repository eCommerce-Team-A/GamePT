package com.example.gamePT.domain.expert.controller;

import com.example.gamePT.domain.expert.entity.Expert;
import com.example.gamePT.domain.expert.service.ExpertService;
import com.example.gamePT.domain.user.entity.SiteUser;
import com.example.gamePT.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/expert")
public class ExpertController {
    private final ExpertService expertService;
    private final UserService userService;

    @GetMapping("/request")
    public String request() {
        return "expert/request";
    }

    @PostMapping("/request")
    public String request(@RequestParam("introduce") String introduce, Principal principal) {
        this.expertService.create(principal.getName(), introduce);
        SiteUser siteUser = this.userService.findByUsername(principal.getName());
        this.userService.approveExpert(siteUser, "Request");
        return "redirect:/";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<Expert> expertList = this.expertService.getExpertList();
        model.addAttribute("expertList", expertList);
        return "expert/list";
    }

    @GetMapping("/approve/{id}")
    public String approve(@PathVariable("id") Long id, Model model) {
        Expert expert = this.expertService.getExpertById(id);
        SiteUser siteUser = this.userService.findByUsername(expert.getUserName());
        this.userService.approveExpert(siteUser, "Expert");
        this.expertService.deleteExpert(expert);
        return "redirect:/expert/list";
    }

    @GetMapping("/reject/{id}")
    public String reject(@PathVariable("id") Long id, Model model) {
        Expert expert = this.expertService.getExpertById(id);
        SiteUser siteUser = this.userService.findByUsername(expert.getUserName());
        this.expertService.deleteExpert(expert);
        return "redirect:/expert/list";
    }

}
