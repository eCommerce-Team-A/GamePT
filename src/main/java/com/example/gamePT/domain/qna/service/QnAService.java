package com.example.gamePT.domain.qna.service;

import com.example.gamePT.domain.qna.entity.QnA;
import com.example.gamePT.domain.qna.repository.QnARepository;
import com.example.gamePT.domain.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QnAService {
    private final QnARepository qnARepository;

    public QnA createQnA(SiteUser author, String title, String content, Boolean isBlind) {
        QnA qnA = QnA.builder()
                .author(author)
                .title(title)
                .content(content)
                .isBlind(isBlind)
                .build();
        this.qnARepository.save(qnA);
        return findById(qnA.getId());
    }

    public QnA update(Long id, String title, String content, Boolean isBlind) {
        QnA _qnA = findById(id);
        QnA qnA = _qnA.toBuilder()
                .title(title)
                .content(content)
                .isBlind(isBlind)
                .build();
        this.qnARepository.save(qnA);

        return qnA;
    }

    public List<QnA> getQnAList() {
        return qnARepository.findAll();
    }

    public QnA findById(Long id) {
        Optional<QnA> _qnA = this.qnARepository.findById(id);
        if (_qnA.isEmpty()) {
            return null;
        }
        return _qnA.get();
    }

    public void delete(Long id) {
        QnA qnA = findById(id);
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
}
