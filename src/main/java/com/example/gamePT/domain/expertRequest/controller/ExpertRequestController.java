package com.example.gamePT.domain.expertRequest.controller;

import com.example.gamePT.domain.expert.service.ExpertService;
import com.example.gamePT.domain.expertRequest.entity.ExpertRequest;
import com.example.gamePT.domain.expertRequest.entity.ExpertRequestWithUser;
import com.example.gamePT.domain.expertRequest.service.ExpertRequestService;
import com.example.gamePT.domain.image.service.ImageService;
import com.example.gamePT.domain.user.entity.SiteUser;
import com.example.gamePT.domain.user.service.UserService;
import com.example.gamePT.global.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/expert/request")
public class ExpertRequestController {
    private final ExpertRequestService expertRequestService;
    private final ExpertService expertService;
    private final UserService userService;
    private final EmailService emailService;
    private final ImageService imageService;

    @GetMapping("")
    public String request() {
        return "expert/request";
    }

    @PostMapping("")
    public String request(@RequestParam("introduce") String introduce, Principal principal,
                          @RequestParam(value = "profileImg") MultipartFile profileImg) throws IOException {
        ExpertRequest expertRequest = this.expertRequestService.createExpertRequest(principal.getName(), introduce);
        this.imageService.saveRequestProfile(expertRequest, profileImg);
        SiteUser siteUser = this.userService.findByUsername(principal.getName());
        this.userService.approveExpert(siteUser, "Request");
        return "redirect:/";
    }

    @GetMapping("/list")
    public String requestList(Model model) {
        List<ExpertRequest> expertRequestList = this.expertRequestService.getExpertRequestList();
        model.addAttribute("expertRequestList", expertRequestList);
        return "expert/request_list";
    }

    @GetMapping("/{id}")
    public String requestDetail(@PathVariable("id")Long id, Model model) {
        ExpertRequest expertRequest = this.expertRequestService.getExpertRequestById(id);
        String requestImg = this.imageService.getRequestImg(expertRequest.getId());
        SiteUser siteUser = this.userService.findByUsername(expertRequest.getUserName());
        String profile = this.imageService.getSiteUserImg(siteUser.getId());
        ExpertRequestWithUser expertRequestWithUser = new ExpertRequestWithUser(siteUser, profile, expertRequest, requestImg);
        model.addAttribute("expertRequestWithUser", expertRequestWithUser);
        return "expert/request_detail";
    }

    @GetMapping("/approve/{id}")
    public String approve(@PathVariable("id") Long id) {
        ExpertRequest expertRequest = this.expertRequestService.getExpertRequestById(id);
        SiteUser siteUser = this.userService.findByUsername(expertRequest.getUserName());
        this.userService.approveExpert(siteUser, "Expert");
        this.expertRequestService.deleteExpertRequest(expertRequest);
        this.emailService.sendApprove(siteUser.getEmail(), true, siteUser);
        this.expertService.createExpert(siteUser.getId());
        return "redirect:/expert/request/list";
    }

    @GetMapping("/reject/{id}")
    public String reject(@PathVariable("id") Long id) {
        ExpertRequest expertRequest = this.expertRequestService.getExpertRequestById(id);
        SiteUser siteUser = this.userService.findByUsername(expertRequest.getUserName());
        this.userService.approveExpert(siteUser, "Member");
        this.expertRequestService.deleteExpertRequest(expertRequest);
        this.emailService.sendApprove(siteUser.getEmail(), false, siteUser);
        return "redirect:/expert/request/list";
    }

}
