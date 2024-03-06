package com.example.gamePT.domain.qna.service;

import com.example.gamePT.domain.qna.entity.QnA;
import com.example.gamePT.domain.qna.repository.QnARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QnAService {
    private final QnARepository qnARepository;

    public List<QnA> getQnAList() {
        return qnARepository.findAll();
    }
}
