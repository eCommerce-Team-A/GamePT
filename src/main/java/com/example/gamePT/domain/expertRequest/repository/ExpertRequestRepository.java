package com.example.gamePT.domain.expertRequest.repository;

import com.example.gamePT.domain.expertRequest.entity.ExpertRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExpertRequestRepository extends JpaRepository<ExpertRequest, Long> {
    Optional<ExpertRequest> findByUserName(String username);
}
