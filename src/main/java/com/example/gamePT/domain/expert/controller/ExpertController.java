package com.example.gamePT.domain.expert.controller;

import com.example.gamePT.domain.career.entity.Career;
import com.example.gamePT.domain.career.service.CareerService;
import com.example.gamePT.domain.course.entity.Course;
import com.example.gamePT.domain.course.service.CourseService;
import com.example.gamePT.domain.expert.entity.Expert;
import com.example.gamePT.domain.expert.service.ExpertService;
import com.example.gamePT.domain.review.service.ReviewService;
import com.example.gamePT.domain.user.entity.SiteUser;
import com.example.gamePT.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/expert")
public class ExpertController {
    private final UserService userService;
    private final CourseService courseService;
    private final ReviewService reviewService;
    private final CareerService careerService;
    private final ExpertService expertService;


    //전문가 목록
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/list")
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
    public String expertDetail(@PathVariable("username") String username, Model model) {
        this.passData(username, model);
        return "expert/detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{username}")
    public String modifyExpert(@PathVariable("username") String username, Model model) {
        this.passData(username, model);
        return "expert/modify";
    }

    @PostMapping("/modify/{username}")
    public String modifyExpert(@PathVariable("username") String username, @RequestParam("introduce") String introduce) {
        SiteUser siteUser = this.userService.findByUsername(username);
        Expert expert = this.expertService.getExpertBySiteUserId(siteUser.getId());
        this.expertService.modifyExpert(expert, introduce);
        return String.format("redirect:/expert/detail/%s", username);
    }

    public void passData(String username, Model model) {
        SiteUser siteUser = this.userService.findByUsername(username);
        Expert expert = this.expertService.getExpertBySiteUserId(siteUser.getId());
        String profileImg = this.userService.getProfileImg(siteUser.getId());
        List<Career> careerList = this.careerService.getCareerListByExpertId(expert.getId());
        List<Course> courseList = this.courseService.findCourseByAuthorId(siteUser.getId());
        Map<String, Course> scoreCourseList = new HashMap<>();
        for (Course course : courseList) {
            scoreCourseList.put(this.reviewService.getScoreAvg(course.getId()), course);
        }
        model.addAttribute("siteUser", siteUser);
        model.addAttribute("introduce", expert.getIntroduce());
        model.addAttribute("profileImg", profileImg);
        model.addAttribute("scoreCourseList", scoreCourseList);
        model.addAttribute("careerList", careerList);
    }

}
