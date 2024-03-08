package com.example.gamePT.domain.career.repository;

import com.example.gamePT.domain.career.entity.Career;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CareerRepository extends JpaRepository<Career, Long> {
    public List<Career> findCareersByExpert_Id(Long id);

}
