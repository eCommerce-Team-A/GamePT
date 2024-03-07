package com.example.gamePT.domain.career.repository;

import com.example.gamePT.domain.career.entity.Career;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CareerRepository extends JpaRepository<Career, Long> {
    public List<Career> findCareersByUsername(String username);

    public Optional<Career> findCareerByUsername(String username);
}
