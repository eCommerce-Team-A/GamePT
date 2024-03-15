package com.example.gamePT.domain.expertRequest.entity;

import com.example.gamePT.domain.user.entity.SiteUser;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ExpertRequestWithUser {
    SiteUser siteUser;
    String profile;
    ExpertRequest expertRequest;
    String requestImg;
    public ExpertRequestWithUser(SiteUser siteUser, String profile, ExpertRequest expertRequest, String requestImg) {
        this.siteUser = siteUser;
        this.profile = profile;
        this.expertRequest = expertRequest;
        this.requestImg = requestImg;
    }
}
