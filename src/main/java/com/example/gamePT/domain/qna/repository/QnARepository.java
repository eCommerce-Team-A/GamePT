package com.example.gamePT.domain.qna.repository;

import com.example.gamePT.domain.qna.entity.QnA;
import com.example.gamePT.domain.user.entity.SiteUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QnARepository extends JpaRepository<QnA, Long> {
    Page<QnA> findAll(Pageable pageable);

    Page<QnA> findAllByAuthor(SiteUser su, Pageable pageable);
}
