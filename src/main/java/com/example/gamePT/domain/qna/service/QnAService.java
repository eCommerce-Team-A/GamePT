package com.example.gamePT.domain.qna.service;

import com.example.gamePT.domain.qna.entity.QnA;
import com.example.gamePT.domain.qna.entity.QnACreateForm;
import com.example.gamePT.domain.qna.repository.QnARepository;
import com.example.gamePT.domain.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QnAService {
    private final QnARepository qnARepository;

    public QnA createQnA(SiteUser author, QnACreateForm qnACreateForm) {

        QnA qnA = QnA.builder()
                .author(author)
                .title(qnACreateForm.getTitle())
                .content(qnACreateForm.getContent())
                .isBlind(qnACreateForm.getIsBlind())
                .isAnswered(false)
                .build();

        this.qnARepository.save(qnA);
        return findById(qnA.getId());
    }

    public QnA update(QnACreateForm qnACreateForm) {

        QnA _qnA = findById(qnACreateForm.getId());
        QnA qnA = _qnA.toBuilder()
                .title(qnACreateForm.getTitle())
                .content(qnACreateForm.getContent())
                .isBlind(qnACreateForm.getIsBlind())
                .build();
        this.qnARepository.save(qnA);

        return qnA;
    }

    public Page<QnA> getQnAList(int page) {

        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));

        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));

        return qnARepository.findAll(pageable);
    }

    public QnA findById(Long id) {
        Optional<QnA> _qnA = this.qnARepository.findById(id);
        if (_qnA.isEmpty()) {
            return null;
        }
        return _qnA.get();
    }

    public void delete(QnA qnA) {

        this.qnARepository.delete(qnA);
    }

    public void createAnswer(QnA _qnA, String answer) {
        QnA qnA = _qnA.toBuilder()
                .isAnswered(true)
                .answer(answer)
                .answerDate(LocalDateTime.now())
                .build();
        this.qnARepository.save(qnA);
    }

    public void updateAnswer(QnA _qnA, String answer) {
        QnA qnA = _qnA.toBuilder()
                .answer(answer)
                .answerDate(LocalDateTime.now())
                .build();
        this.qnARepository.save(qnA);
    }

    public Page<QnA> getListForMyPage(int page, SiteUser su) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page, 5, Sort.by(sorts));
        return this.qnARepository.findAllByAuthor(su, pageable);
    }
}
