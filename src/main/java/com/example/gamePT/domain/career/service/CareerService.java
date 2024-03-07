package com.example.gamePT.domain.career.service;

import com.example.gamePT.domain.career.entity.Career;
import com.example.gamePT.domain.career.repository.CareerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CareerService {
    private final CareerRepository careerRepository;

    public List<Career> getCareerListByUsername(String username) {
        return this.careerRepository.findCareersByUsername(username);
    }

    public Career getCareerById(Long id) {
        Optional<Career> career = this.careerRepository.findById(id);
        if (career.isEmpty()) {
            return null;
        }
        return career.get();
    }

    public void registerIntroduce(String username, String introduce) {
        Career career = Career.builder()
                .username(username)
                .introduce(introduce)
                .build();
        this.careerRepository.save(career);
    }

    public void modifyIntroduce(String username, String introduce) {
        List<Career> careerList = this.getCareerListByUsername(username);
        for (Career career : careerList) {
            Career modifyCareer = career.toBuilder()
                    .introduce(introduce)
                    .build();
            this.careerRepository.save(modifyCareer);
        }
    }

    public void registerCareer(String username, String introduce, String category, String content) {
        Career career = Career.builder()
                .username(username)
                .introduce(introduce)
                .category(category)
                .content(content)
                .build();
        this.careerRepository.save(career);
    }

    public void deleteCareer(Career career) {
        this.careerRepository.delete(career);
    }

    public String getIntroduce(List<Career> careerList) {
        if (!careerList.isEmpty()) {
            return careerList.get(0).getIntroduce();
        }
        return null;
    }
}
