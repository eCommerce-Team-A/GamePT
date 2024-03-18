package com.example.gamePT.domain.expert.controller;

import com.example.gamePT.domain.career.entity.Career;
import com.example.gamePT.domain.career.service.CareerService;
import com.example.gamePT.domain.careerCategory.entity.Category;
import com.example.gamePT.domain.careerCategory.service.CategoryService;
import com.example.gamePT.domain.course.entity.Course;
import com.example.gamePT.domain.course.service.CourseService;
import com.example.gamePT.domain.expert.entity.CourseScore;
import com.example.gamePT.domain.expert.entity.Expert;
import com.example.gamePT.domain.expert.entity.SiteUserWithImg;
import com.example.gamePT.domain.expert.service.ExpertService;
import com.example.gamePT.domain.orderItem.entity.OrderItem;
import com.example.gamePT.domain.orderItem.service.OrderItemService;
import com.example.gamePT.domain.qna.entity.QnA;
import com.example.gamePT.domain.review.service.ReviewService;
import com.example.gamePT.domain.user.entity.SiteUser;
import com.example.gamePT.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/expert")
public class ExpertController {
    private final UserService userService;
    private final CourseService courseService;
    private final ReviewService reviewService;
    private final CareerService careerService;
    private final ExpertService expertService;
    private final OrderItemService orderItemService;
    private final CategoryService categoryService;


    //전문가 목록
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/list")
    public String expertList(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {

        Page<SiteUser> expertUserList = this.userService.getUserListByAuthorization("Expert",page);
        Page<SiteUserWithImg> siteUserWithImgList = toDtoList(expertUserList);

        model.addAttribute("siteUserWithImgList", siteUserWithImgList);
        return "expert/user_list";
    }

    public Page<SiteUserWithImg> toDtoList(Page<SiteUser> expertUserList){
        Page<SiteUserWithImg> SiteUserWithImgList = expertUserList.map(m -> SiteUserWithImg.builder()
                .siteUser(m)
                .img(this.userService.getProfileImg(m.getId()))
                .expert(this.expertService.getExpertBySiteUserId(m.getId()))
                .build());
        return SiteUserWithImgList;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/detail/{username}")
    public String expertDetail(@PathVariable("username") String username, Model model) {
        this.passData(username, model);
        return "expert/detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/detail/orderItem/{pageNumber}")
    public String qnaReload(@PathVariable(value = "pageNumber") int pageNumber, Model model){

        SiteUser su = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Page<OrderItem> orderItemList = this.orderItemService.findByAuthor(pageNumber,su);

        model.addAttribute("orderItemList", orderItemList);

        return "expert/detail::#orderItemHistory";
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
        Page<OrderItem> orderItemList = this.orderItemService.findByAuthor(0,siteUser);
        List<Category> categoryList = this.categoryService.getCategoryList();
        List<CourseScore> courseScoreList = this.getCourseScoreList(this.courseService.findCourseByAuthorId(siteUser.getId()));

        model.addAttribute("siteUser", siteUser);
        model.addAttribute("introduce", expert.getIntroduce());
        model.addAttribute("profileImg", profileImg);
        model.addAttribute("courseScoreList", courseScoreList);
        model.addAttribute("orderItemList", orderItemList);
        model.addAttribute("careerList", careerList);
        model.addAttribute("categoryList", categoryList);
    }

    public List<CourseScore> getCourseScoreList(List<Course> courseList) {
        List<CourseScore> courseScoreList = new ArrayList<>();
        for (Course course : courseList) {
            CourseScore courseScore = new CourseScore(this.reviewService.getScoreAvg(course.getId()), course);
            courseScoreList.add(courseScore);
        }
        return courseScoreList;
    }

}
