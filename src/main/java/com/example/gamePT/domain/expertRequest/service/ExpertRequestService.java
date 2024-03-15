package com.example.gamePT.domain.expertRequest.service;

import com.example.gamePT.domain.expertRequest.entity.ExpertRequest;
import com.example.gamePT.domain.expertRequest.repository.ExpertRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpertRequestService {
    private final ExpertRequestRepository expertRequestRepository;

    public ExpertRequest createExpertRequest(String userName, String content) {
        ExpertRequest expertRequest = ExpertRequest.builder()
                .userName(userName)
                .content(content)
                .build();
                this.expertRequestRepository.save(expertRequest);
        return this.getExpertRequestById(expertRequest.getId());
    }

    public List<ExpertRequest> getExpertRequestList() {
        return this.expertRequestRepository.findAll();
    }

    public ExpertRequest getExpertRequestById(Long id) {
        if (this.expertRequestRepository.findById(id).isEmpty()) {
            return null;
        }
        return this.expertRequestRepository.findById(id).get();
    }

    public ExpertRequest getExpertRequestByUsername(String username) {
        if (this.expertRequestRepository.findByUserName(username).isEmpty()) {
            return null;
        }
        return this.expertRequestRepository.findByUserName(username).get();
    }

    public void deleteExpertRequest(ExpertRequest expertRequest) {
        this.expertRequestRepository.delete(expertRequest);
    }
}
