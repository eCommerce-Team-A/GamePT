package com.example.gamePT.domain.qna.controller;

import com.example.gamePT.domain.qna.entity.QnA;
import com.example.gamePT.domain.qna.service.QnAService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/qna")
public class QnAController {

    private final QnAService qnAService;

    @GetMapping("/list")
    public String getQnAList(Model model){

        List<QnA> qnAList = qnAService.getQnAList();

        model.addAttribute("qnAList", qnAList);

        return "qna/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String postFromQnA(QnA qnA){
        return "qna/create";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String postQnA(@Valid QnA qnA, BindingResult br, Model model){

        List<QnA> qnAList = qnAService.getQnAList();

        model.addAttribute("qnAList", qnAList);

        return "redirect:/qna/list";
    }

}
