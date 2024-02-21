package com.example.gamePT.domain.duoArticle.repository;

import com.example.gamePT.domain.duoArticle.enity.DuoArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DuoArticleRepository extends JpaRepository<DuoArticle, Long> {

}
