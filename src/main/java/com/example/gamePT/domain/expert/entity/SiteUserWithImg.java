package com.example.gamePT.domain.expert.entity;

import com.example.gamePT.domain.user.entity.SiteUser;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
@Builder
public class SiteUserWithImg {
    String img;
    SiteUser siteUser;
    Expert expert;
    public SiteUserWithImg(String img, SiteUser siteUser, Expert expert) {
        this.img = img;
        this.siteUser = siteUser;
        this.expert = expert;
    }



}
