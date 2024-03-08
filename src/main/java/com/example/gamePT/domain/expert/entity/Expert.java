package com.example.gamePT.domain.expert.entity;

import com.example.gamePT.domain.career.entity.Career;
import com.example.gamePT.global.jpa.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Expert extends BaseEntity {
    private Long siteUserId;
    private String introduce;
    @OneToMany(mappedBy = "expert", cascade = CascadeType.REMOVE)
    private List<Career> careerList;
}
