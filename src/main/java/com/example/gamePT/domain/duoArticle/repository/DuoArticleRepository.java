package com.example.gamePT.domain.duoArticle.repository;

import com.example.gamePT.domain.duoArticle.enity.DuoArticle;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DuoArticleRepository extends JpaRepository<DuoArticle, Long> {
    List<DuoArticle> findByMyLine(String myLine, Sort sort);
}
