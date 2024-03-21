package com.example.gamePT.domain.rebate.entity;

import com.example.gamePT.domain.user.entity.SiteUser;
import com.example.gamePT.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class Rebate extends BaseEntity {

    @ManyToOne
    private SiteUser seller;

    private Integer salesYear;

    private Integer salesMonth;

    private Long salesNumber;

    private Long salesPrice;
}
