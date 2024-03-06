package com.example.gamePT.domain.expert.controller;

import com.example.gamePT.domain.course.entity.Course;
import com.example.gamePT.domain.course.service.CourseService;
import com.example.gamePT.domain.expert.entity.Expert;
import com.example.gamePT.domain.expert.service.ExpertService;
import com.example.gamePT.domain.review.service.ReviewService;
import com.example.gamePT.domain.user.entity.SiteUser;
import com.example.gamePT.domain.user.service.UserService;
import com.example.gamePT.global.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/expert")
public class ExpertController {
    private final ExpertService expertService;
    private final UserService userService;
    private final EmailService emailService;
    private final CourseService courseService;
    private final ReviewService reviewService;

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

    @GetMapping("/request/list")
    public String requestList(Model model) {
        List<Expert> expertList = this.expertService.getExpertList();
        model.addAttribute("expertList", expertList);
        return "expert/request_list";
    }

    @GetMapping("/approve/{id}")
    public String approve(@PathVariable("id") Long id) {
        Expert expert = this.expertService.getExpertById(id);
        SiteUser siteUser = this.userService.findByUsername(expert.getUserName());
        this.userService.approveExpert(siteUser, "Expert");
        this.expertService.deleteExpert(expert);
        this.emailService.sendApprove(siteUser.getEmail(), true, siteUser);
        return "redirect:/expert/request/list";
    }

    @GetMapping("/reject/{id}")
    public String reject(@PathVariable("id") Long id) {
        Expert expert = this.expertService.getExpertById(id);
        SiteUser siteUser = this.userService.findByUsername(expert.getUserName());
        this.userService.approveExpert(siteUser, "Member");
        this.expertService.deleteExpert(expert);
        this.emailService.sendApprove(siteUser.getEmail(), false, siteUser);
        return "redirect:/expert/request/list";
    }

    //전문가 목록
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/users")
    public String expertList(Model model) {
        List<SiteUser> expertUserList = this.userService.getUserListByAuthorization("Expert");
        Map<String, SiteUser> expertUserImgList = new HashMap<>();
        for (SiteUser expert : expertUserList) {
            expertUserImgList.put(this.userService.getProfileImg(expert.getId()), expert);
        }
        model.addAttribute("expertUserImgList", expertUserImgList);
        return "expert/user_list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/detail/{username}")
    public String detail(@PathVariable("username") String username, Model model) {
        SiteUser siteUser = this.userService.findByUsername(username);
        String profileImg = this.userService.getProfileImg(siteUser.getId());
        List<Course> courseList = this.courseService.findCourseByAuthorId(siteUser.getId());
        Map<String, Course> scoreCourseList = new HashMap<>();
        for (Course course : courseList) {
            scoreCourseList.put(this.reviewService.getScoreAvg(course.getId()), course);
        }
        model.addAttribute("siteUser", siteUser);
        model.addAttribute("profileImg", profileImg);
        model.addAttribute("scoreCourseList", scoreCourseList);
        return "expert/detail";
    }

}
