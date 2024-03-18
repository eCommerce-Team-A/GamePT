package com.example.gamePT.domain.home.controller;

import com.example.gamePT.domain.course.entity.Course;
import com.example.gamePT.domain.course.service.CourseService;
import com.example.gamePT.domain.expert.entity.SiteUserWithImg;
import com.example.gamePT.domain.expert.service.ExpertService;
import com.example.gamePT.domain.user.entity.SiteUser;
import com.example.gamePT.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;
import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;
    private final CourseService courseService;
    private final ExpertService expertService;

    @SneakyThrows
    @GetMapping("/home/search")
    public String search(@RequestParam(value = "category", defaultValue = "course") String searchCategory, @RequestParam(value = "kw", defaultValue = "") String searchKw) {

        searchKw = URLEncoder.encode(searchKw);
        return "redirect:/"+searchCategory+"/list?kw="+searchKw+"&page=0";
    }

    @GetMapping
    public String index(Model model) {

        List<Course> courseList = courseService.getCourseForMain();

        List<SiteUser> expertUserList = this.userService.getUserListByAuthorizationForMain("Expert");
        List<SiteUserWithImg> siteUserWithImgList = toDtoList(expertUserList);

        model.addAttribute("courseList",courseList);
        model.addAttribute("siteUserWithImgList",siteUserWithImgList);

        return "home/index";
    }

    public List<SiteUserWithImg> toDtoList(List<SiteUser> expertUserList){
        List<SiteUserWithImg> SiteUserWithImgList = expertUserList.stream().map(m -> SiteUserWithImg.builder()
                .siteUser(m)
                .img(this.userService.getProfileImg(m.getId()))
                .expert(this.expertService.getExpertBySiteUserId(m.getId()))
                .build()).toList();

        return SiteUserWithImgList;
    }
}
