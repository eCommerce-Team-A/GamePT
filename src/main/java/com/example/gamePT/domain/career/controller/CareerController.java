package com.example.gamePT.domain.career.controller;

import com.example.gamePT.domain.career.entity.Career;
import com.example.gamePT.domain.career.service.CareerService;
import com.example.gamePT.domain.course.entity.Course;
import com.example.gamePT.domain.course.service.CourseService;
import com.example.gamePT.domain.review.service.ReviewService;
import com.example.gamePT.domain.user.entity.SiteUser;
import com.example.gamePT.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/career")
@RequiredArgsConstructor
public class CareerController {
    private final CareerService careerService;
    private final UserService userService;
    private final CourseService courseService;
    private final ReviewService reviewService;

    @GetMapping("/create/{username}")
    public String createCareer(@PathVariable("username") String username, Model model) {
        List<Career> careerList = this.careerService.getCareerListByUsername(username);
        SiteUser siteUser = this.userService.findByUsername(username);
        String profileImg = this.userService.getProfileImg(siteUser.getId());
        List<Course> courseList = this.courseService.findCourseByAuthorId(siteUser.getId());
        Map<String, Course> scoreCourseList = new HashMap<>();
        for (Course course : courseList) {
            scoreCourseList.put(this.reviewService.getScoreAvg(course.getId()), course);
        }
        String introduce = this.careerService.getIntroduce(careerList);
        model.addAttribute("siteUser", siteUser);
        model.addAttribute("profileImg", profileImg);
        model.addAttribute("scoreCourseList", scoreCourseList);
        model.addAttribute("careerList", careerList);
        model.addAttribute("introduce", introduce);
        return "expert/career_form";
    }

    @PostMapping("/add/{username}")
    public String addCareer(@PathVariable("username") String username, Model model,
                            @RequestParam("category") String category, @RequestParam("content") String content) {
        List<Career> careerList = this.careerService.getCareerListByUsername(username);
        String introduce = this.careerService.getIntroduce(careerList);
        this.careerService.registerCareer(username, introduce, category,content);
        return String.format("redirect:/career/create/%s", username);
    }

    @PostMapping("/introduce/{username}")
    public String registerIntroduce(@PathVariable("username") String username, @RequestParam("introduce") String introduce) {
        List<Career> careerList = this.careerService.getCareerListByUsername(username);
        if (careerList.isEmpty()) {
            this.careerService.registerIntroduce(username, introduce);
        } else {
            this.careerService.modifyIntroduce(username, introduce);
        }
        return String.format("redirect:/expert/detail/%s", username);
    }

    @GetMapping("/delete/{careerId}")
    public String deleteCareer(@PathVariable("careerId") Long careerId) {
        Career career = this.careerService.getCareerById(careerId);
        this.careerService.deleteCareer(career);
        return String.format("redirect:/career/create/%s", career.getUsername());
    }
}
