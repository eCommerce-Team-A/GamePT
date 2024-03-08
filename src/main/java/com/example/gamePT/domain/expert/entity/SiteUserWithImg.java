package com.example.gamePT.domain.expert.entity;

import com.example.gamePT.domain.user.entity.SiteUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SiteUserWithImg {
    String img;
    SiteUser siteUser;
    public SiteUserWithImg(String img, SiteUser siteUser) {
        this.img = img;
        this.siteUser = siteUser;
    }
}
