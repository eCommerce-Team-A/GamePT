package com.example.gamePT.domain.career.service;

import com.example.gamePT.domain.career.repository.CareerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CareerService {
    private final CareerRepository careerRepository;
}
