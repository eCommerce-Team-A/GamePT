package com.example.gamePT.domain.rebate.service;

import com.example.gamePT.domain.rebate.entity.Rebate;
import com.example.gamePT.domain.rebate.repository.RebateRepository;
import com.example.gamePT.domain.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RebateService {

    private final RebateRepository rebateRepository;

    public void create(Rebate rebate){
        rebateRepository.save(rebate);
    }

    public Rebate findBySellerAndSalePeriod(SiteUser seller, Integer year, Integer month){

        Optional<Rebate> rebate = rebateRepository.findBySellerAndSalePeriod(seller.getId(),year,month);

        return rebate.orElse(null);
    }

}
