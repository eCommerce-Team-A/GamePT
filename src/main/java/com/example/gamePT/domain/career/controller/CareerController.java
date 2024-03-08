package com.example.gamePT.domain.career.controller;

import com.example.gamePT.domain.career.entity.Career;
import com.example.gamePT.domain.career.service.CareerService;
import com.example.gamePT.domain.course.service.CourseService;
import com.example.gamePT.domain.expert.entity.Expert;
import com.example.gamePT.domain.expert.service.ExpertService;
import com.example.gamePT.domain.review.service.ReviewService;
import com.example.gamePT.domain.user.entity.SiteUser;
import com.example.gamePT.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/career")
@RequiredArgsConstructor
public class CareerController {
    private final CareerService careerService;
    private final UserService userService;
    private final ExpertService expertService;
    private final CourseService courseService;
    private final ReviewService reviewService;



    @PostMapping("/add/{username}")
    public String addCareer(@PathVariable("username") String username,
                            @RequestParam("category") String category, @RequestParam("content") String content) {
        SiteUser siteUser = this.userService.findByUsername(username);
        Expert expert = this.expertService.getExpertBySiteUserId(siteUser.getId());
        this.careerService.createCareer(category, content, expert);
        return String.format("redirect:/expert/modify/%s", username);
    }


    @GetMapping("/delete/{careerId}")
    public String deleteCareer(@PathVariable("careerId") Long careerId) {
        Career career = this.careerService.getCareerById(careerId);
        SiteUser siteUser = this.userService.findByUserId(career.getExpert().getSiteUserId());
        this.careerService.deleteCareer(career);
        return String.format("redirect:/expert/modify/%s", siteUser.getUsername());
    }
}
