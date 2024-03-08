package com.example.gamePT.domain.expert.service;

import com.example.gamePT.domain.expert.entity.Expert;
import com.example.gamePT.domain.expert.repository.ExpertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpertService {
    private final ExpertRepository expertRepository;

    public void createExpert(Long id) {
        Expert expert = Expert.builder()
                .siteUserId(id)
                .build();
        this.expertRepository.save(expert);
    }

    public void modifyExpert(Expert expert, String introduce) {
        Expert modifyExpert= expert.toBuilder()
                .introduce(introduce)
                .build();
        this.expertRepository.save(modifyExpert);
    }

    public List<Expert> getExpertList() {
        return this.expertRepository.findAll();
    }

    public Expert getExpertById(Long id) {
        if (this.expertRepository.findById(id).isEmpty()) {
            return null;
        }
        return this.expertRepository.findById(id).get();
    }

    public Expert getExpertBySiteUserId(Long id) {
        if (this.expertRepository.findBySiteUserId(id).isEmpty()) {
            return null;
        }
        return this.expertRepository.findBySiteUserId(id).get();
    }

    public void deleteExpert(Expert expert) {
        this.expertRepository.delete(expert);
    }
}
