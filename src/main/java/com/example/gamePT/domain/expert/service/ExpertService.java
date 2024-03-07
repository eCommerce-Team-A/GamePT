package com.example.gamePT.domain.expert.service;

import com.example.gamePT.domain.expert.entity.Expert;
import com.example.gamePT.domain.expert.repository.ExpertReository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpertService {
    private final ExpertReository expertReository;

    public void create(String userName, String introduce) {
        Expert expert = Expert.builder()
                .userName(userName)
                .introduce(introduce)
                .build();
        this.expertReository.save(expert);
    }

    public List<Expert> getExpertList() {
        return this.expertReository.findAll();
    }

    public Expert getExpertById(Long id) {
        if (this.expertReository.findById(id).isEmpty()) {
            return null;
        }
        return this.expertReository.findById(id).get();
    }

    public Expert getExpertByUsername(String username) {
        if (this.expertReository.findByUserName(username).isEmpty()) {
            return null;
        }
        return this.expertReository.findByUserName(username).get();
    }

    public void deleteExpert(Expert expert) {
        this.expertReository.delete(expert);
    }
}
