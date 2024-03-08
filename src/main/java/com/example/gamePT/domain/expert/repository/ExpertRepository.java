package com.example.gamePT.domain.expert.repository;

import com.example.gamePT.domain.expert.entity.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExpertRepository extends JpaRepository<Expert, Long> {

    Optional<Expert> findBySiteUserId(Long id);
}
