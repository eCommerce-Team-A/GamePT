package com.example.gamePT.domain.qna.controller;

import com.example.gamePT.domain.qna.entity.QnA;
import com.example.gamePT.domain.qna.entity.QnACreateForm;
import com.example.gamePT.domain.qna.service.QnAService;
import com.example.gamePT.domain.user.entity.SiteUser;
import com.example.gamePT.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/qna")
public class QnAController {

    private final QnAService qnAService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String createQnA(QnACreateForm qnACreateForm) {
        return "qna/create";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String createQnA(@Valid QnACreateForm qnACreateForm, BindingResult bindingResult,
                            Model model, Principal principal) {
        SiteUser author = this.userService.findByUsername(principal.getName());
        QnA qnA = this.qnAService.create(author, qnACreateForm.getTitle(), qnACreateForm.getContent(),
                qnACreateForm.getIsBlind());

        return "redirect:/qna/detail/" + qnA.getId();
    }

    @GetMapping("/detail/{id}")
    public String qnADetail(@PathVariable(value = "id") Long id, Model model) {
        QnA qnA = this.qnAService.findById(id);
        model.addAttribute("qnA", qnA);

        return "qnA_detail";
    }

    @GetMapping("/list")
    public String getQnAList(Model model) {
        List<QnA> qnAList = qnAService.getQnAList();
        model.addAttribute("qnAList", qnAList);

        return "qna/list";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/update/{id}")
    public String updateQnA(@PathVariable(value = "id") Long id, @Valid QnACreateForm qnACreateForm,
                            BindingResult bindingResult, Model model) {
        QnA qnA = this.qnAService.update(id, qnACreateForm.getTitle(), qnACreateForm.getContent(),
                qnACreateForm.getIsBlind());

        return "redirect:/qna/detail/" + qnA.getId();
    }

    @GetMapping("/delete/{id}")
    public String deleteQnA(@PathVariable(value = "id") Long id) {
        this.qnAService.delete(id);
        return "redirect:/qna/list";
    }
}
