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

        //현재유저 가져와서 author 라는 변수명으로 넘기고 QnA 생성 후 생성된 QnA 페이지로 리다이렉트
        return "redirect:/qna/detail/" + qnA.getId();
    }

    // QnA 상세
    @GetMapping("/detail/{id}")
    public String qnADetail(@PathVariable(value = "id") Long id, Model model) {
        QnA qnA = this.qnAService.findById(id);
        model.addAttribute("qnA", qnA);

        //QnA ID 받아서 해당 QnA 로 상세 정보 표시 하는 html 파일경로 리턴
        return "qna/detail";
    }

    // QnA 목록
    @GetMapping("/list")
    public String getQnAList(Model model) {
        List<QnA> qnAList = qnAService.getQnAList();
        model.addAttribute("qnAList", qnAList);

        // 모든 QnA 리스트 받아서 목록 출력해주는 html로 이동시킴.
        // 제일 먼저 들어온 문의부터 처리해야할거같아서 따로 정렬은 안했습니다.
        return "qna/list";
    }

    // QnA 수정
    @GetMapping("/update/{id}")
    public String updateQnA(@PathVariable(value = "id") Long id, QnACreateForm qnACreateForm, Model model) {
        QnA qnA = this.qnAService.findById(id);
        model.addAttribute("qnA", qnA);

        // 현재 QnA 페이지에서 수정시 QnA ID 받아고 해당 내용담아서 update html 이동 시켰습니다.
        // update.html 로 이동시키거나 아니면 굳이 수정페이지로 안넘기고 해당 QnA 페이지에서 수정시켜도 상관없을듯합니다.
        return "qna/update";
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

        // 일단 get요청 받으면 해당 QnA 그냥 삭제하는 로직으로 짰습니다.
        return "redirect:/qna/list";
    }

    // QnA 답변 생성
    @PostMapping("/answer/{qnAId}")
    public String createAnswer(@PathVariable(value = "qnAId") Long qnAId, @Valid QnAAnswerForm qnAAnswerForm,
                               BindingResult bindingResult, Model model) {
        QnA qnA = this.qnAService.findById(qnAId);
        this.qnAService.createAnswer(qnA, qnAAnswerForm.getAnswer());
        model.addAttribute("qnA", qnA);

        // 해당 Post 요청 받으면  answerForm객체안의 답변내용(answer)로 해당 QnA객체 수정하게 했습니다.
        // 수정되면 해당 QnA로 리다이렉트 시켰습니다.
        return "redirect:/qna/detail/" + qnAId;
    }

    // QnA 답변 수정
    @PostMapping("/answer/update/{qnAId}")
    public String updateAnswer(@PathVariable(value = "qnAId") Long qnAId, @Valid QnAAnswerForm qnAAnswerForm,
                               BindingResult bindingResult, Model model) {
        QnA qnA = this.qnAService.findById(qnAId);
        this.qnAService.updateAnswer(qnA, qnAAnswerForm.getAnswer());
        model.addAttribute("qnA", qnA);

        // createAnswer랑 비슷하게 해당 QnA 객체에 answer만 수정시키는 형식으로 했습니다.
        return "redirect:/qna/detail/" + qnAId;
    }

    // create, update, list html 필요.
}
