package com.example.gamePT.domain.qna.controller;

import com.example.gamePT.domain.qna.entity.QnA;
import com.example.gamePT.domain.qna.entity.QnAAnswerForm;
import com.example.gamePT.domain.qna.entity.QnACreateForm;
import com.example.gamePT.domain.qna.service.QnAService;
import com.example.gamePT.domain.user.entity.SiteUser;
import com.example.gamePT.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    public String createQnA(@Valid QnACreateForm qnACreateForm, BindingResult bindingResult, Principal principal) {

        if(bindingResult.hasErrors()){
            return "qna/create";
        }

        SiteUser author = this.userService.findByUsername(principal.getName());
        QnA qnA = this.qnAService.createQnA(author, qnACreateForm);

        //현재유저 가져와서 author 라는 변수명으로 넘기고 QnA 생성 후 생성된 QnA 페이지로 리다이렉트
//        return "redirect:/qna/detail/" + qnA.getId();
        return "redirect:/qna/list";
    }

    // QnA 상세
    @GetMapping("/detail/{id}")
    public String qnADetail(@PathVariable(value = "id") Long id,QnAAnswerForm qnAAnswerForm, Model model) {

        QnA qnA = this.qnAService.findById(id);

        if(qnA.getIsBlind()){
            if( !qnA.getAuthor().getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName())
                 && !SecurityContextHolder.getContext().getAuthentication().getName().equals("admin")
            ){
                return "history_back";
            }
        }

        model.addAttribute("qnA", qnA);

        return "qna/detail";
    }

    // QnA 목록
    @GetMapping("/list")
    public String getQnAList(Model model,@RequestParam(value="page", defaultValue="0") int page) {

        Page<QnA> paging = this.qnAService.getQnAList(page);
        model.addAttribute("paging", paging);

        // 모든 QnA 리스트 받아서 목록 출력해주는 html로 이동시킴.
        // 제일 먼저 들어온 문의부터 처리해야할거같아서 따로 정렬은 안했습니다.
        return "qna/list";
    }

    // QnA 수정
    @GetMapping("/update/{id}")
    public String updateQnA(@PathVariable(value = "id") Long id, QnACreateForm qnACreateForm) {

        QnA qnA = this.qnAService.findById(id);

        if(!qnA.getAuthor().getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName())){
            return "history_back";
        }

        qnACreateForm.setId(id);
        qnACreateForm.setTitle(qnA.getTitle());
        qnACreateForm.setContent(qnA.getContent());
        qnACreateForm.setIsBlind(qnA.getIsBlind());

        return "qna/update";
    }

    // QnA 수정
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/update")
    public String updateQnA(@Valid QnACreateForm qnACreateForm,
                            BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()){
            return "qna/update";
        }

        QnA qnA = this.qnAService.update(qnACreateForm);

        return "redirect:/qna/detail/" + qnA.getId();
    }

    // QnA 삭제
    @GetMapping("/delete/{id}")
    public String deleteQnA(@PathVariable(value = "id") Long id) {

        QnA qnA = this.qnAService.findById(id);

        if(!qnA.getAuthor().getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName())){
            return "history_back";
        }

        this.qnAService.delete(qnA);


        return "redirect:/qna/list";
    }

    // QnA 답변 생성
    @PostMapping("/answer/{qnAId}")
    public String createAnswer(@PathVariable(value = "qnAId") Long qnAId, @Valid QnAAnswerForm qnAAnswerForm,
                               BindingResult bindingResult, Model model) {

        QnA qnA = this.qnAService.findById(qnAId);

        if(bindingResult.hasErrors()){
            model.addAttribute("qnA",qnA);
            return "qna/detail";
        }

        this.qnAService.createAnswer(qnA, qnAAnswerForm.getAnswer());

        // 해당 Post 요청 받으면  answerForm객체안의 답변내용(answer)로 해당 QnA객체 수정하게 했습니다.
        // 수정되면 해당 QnA로 리다이렉트 시켰습니다.
        return "redirect:/qna/detail/" + qnAId;
    }

    // QnA 답변 수정
    @GetMapping("/answerUpdate/{qnAId}")
    public String answerUpdate(@PathVariable(value = "qnAId") Long qnAId, QnAAnswerForm qnAAnswerForm,
                               Model model) {

        if(!SecurityContextHolder.getContext().getAuthentication().getName().equals("admin")){
            return "history_back";
        }

        QnA qnA = this.qnAService.findById(qnAId);

        model.addAttribute("qnA", qnA);

        return "/qna/answerUpdate";
    }

    @PostMapping("/doAnswerUpdate/{qnAId}")
    public String updateAnswer(@PathVariable(value = "qnAId") Long qnAId, @Valid QnAAnswerForm qnAAnswerForm,
                               BindingResult bindingResult, Model model) {

        if(!SecurityContextHolder.getContext().getAuthentication().getName().equals("admin")){
            return "history_back";
        }

        QnA qnA = this.qnAService.findById(qnAId);

        if(bindingResult.hasErrors()){
            model.addAttribute("qnA",qnA);
            return "/qna/answerUpdate";
        }

        this.qnAService.updateAnswer(qnA, qnAAnswerForm.getAnswer());
        model.addAttribute("qnA", qnA);

        return "redirect:/qna/detail/" + qnAId;
    }

}
