package com.example.gamePT.domain.qna.controller;

import com.example.gamePT.domain.qna.entity.QnA;
import com.example.gamePT.domain.qna.entity.QnAAnswerForm;
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

    //QnA 생성
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String createQnA(QnACreateForm qnACreateForm) {
        return "qna/create";
    }

    // QnA 생성
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String createQnA(@Valid QnACreateForm qnACreateForm, BindingResult bindingResult,
                            Model model, Principal principal) {
        SiteUser author = this.userService.findByUsername(principal.getName());
        QnA qnA = this.qnAService.createQnA(author, qnACreateForm.getTitle(), qnACreateForm.getContent(),
                qnACreateForm.getIsBlind());

        return "redirect:/qna/detail/" + qnA.getId();
    }

    // QnA 상세
    @GetMapping("/detail/{id}")
    public String qnADetail(@PathVariable(value = "id") Long id, Model model) {
        QnA qnA = this.qnAService.findById(id);
        model.addAttribute("qnA", qnA);

        return "qnA_detail";
    }

    // QnA 목록
    @GetMapping("/list")
    public String getQnAList(Model model) {
        List<QnA> qnAList = qnAService.getQnAList();
        model.addAttribute("qnAList", qnAList);

        return "qna/list";
    }

    // QnA 수정
    @GetMapping("/update/{id}")
    public String updateQnA(@PathVariable(value = "id") Long id, QnACreateForm qnACreateForm, Model model) {
        QnA qnA = this.qnAService.findById(id);
        model.addAttribute("qnA", qnA);

        return "qnA_update";
    }

    // QnA 수정
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/update/{id}")
    public String updateQnA(@PathVariable(value = "id") Long id, @Valid QnACreateForm qnACreateForm,
                            BindingResult bindingResult, Model model) {
        QnA qnA = this.qnAService.update(id, qnACreateForm.getTitle(), qnACreateForm.getContent(),
                qnACreateForm.getIsBlind());

        return "redirect:/qna/detail/" + id;
    }

    // QnA 삭제
    @GetMapping("/delete/{id}")
    public String deleteQnA(@PathVariable(value = "id") Long id) {
        this.qnAService.delete(id);
        return "redirect:/qna/list";
    }

    // QnA 답변 생성
    @PostMapping("/answer/{qnAId}")
    public String createAnswer(@PathVariable(value = "qnAId") Long qnAId, QnAAnswerForm qnAAnswerForm, Model model) {
        QnA qnA = this.qnAService.findById(qnAId);
        this.qnAService.createAnswer(qnA, qnAAnswerForm.getAnswer());
        model.addAttribute("qnA", qnA);

        return "redirect:/qna/detail/" + qnAId;
    }

    // QnA 답변 수정
    @PostMapping("/answer/update/{qnAId}")
    public String updateAnswer(@PathVariable(value = "qnAId") Long qnAId, QnAAnswerForm qnAAnswerForm, Model model) {
        QnA qnA = this.qnAService.findById(qnAId);
        this.qnAService.updateAnswer(qnA, qnAAnswerForm.getAnswer());
        model.addAttribute("qnA", qnA);

        return "redirect:/qna/detail/" + qnAId;
    }
}
