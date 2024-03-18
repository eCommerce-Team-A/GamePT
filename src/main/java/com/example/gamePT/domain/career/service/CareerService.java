package com.example.gamePT.domain.career.service;

import com.example.gamePT.domain.career.entity.Career;
import com.example.gamePT.domain.career.repository.CareerRepository;
import com.example.gamePT.domain.careerCategory.repository.CategoryRepository;
import com.example.gamePT.domain.expert.entity.Expert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public Career createCareer(String category, String icon, String color, String content, Expert expert) {
        Career career = Career.builder()
                .category(category)
                .icon(icon)
                .color(color)
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
