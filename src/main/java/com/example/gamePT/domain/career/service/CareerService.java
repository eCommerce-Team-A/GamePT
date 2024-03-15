package com.example.gamePT.domain.career.service;

import com.example.gamePT.domain.career.entity.Career;
import com.example.gamePT.domain.career.entity.CareerWithCategory;
import com.example.gamePT.domain.career.repository.CareerRepository;
import com.example.gamePT.domain.careerCategory.entity.Category;
import com.example.gamePT.domain.careerCategory.repository.CategoryRepository;
import com.example.gamePT.domain.expert.entity.Expert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CareerService {
    private final CareerRepository careerRepository;
    private final CategoryRepository categoryRepository;

    public List<Career> getCareerListByExpertId(Long id) {
        return this.careerRepository.findCareersByExpert_Id(id);
    }

    public Career getCareerById(Long id) {
        Optional<Career> career = this.careerRepository.findById(id);
        if (career.isEmpty()) {
            return null;
        }
        return career.get();
    }

    public Career createCareer(Long categoryId, String content, Expert expert) {
        Career career = Career.builder()
                .categoryId(categoryId)
                .content(content)
                .expert(expert)
                .build();
        this.careerRepository.save(career);
        return career;
    }

    public void deleteCareer(Career career) {
        this.careerRepository.delete(career);
    }

}
