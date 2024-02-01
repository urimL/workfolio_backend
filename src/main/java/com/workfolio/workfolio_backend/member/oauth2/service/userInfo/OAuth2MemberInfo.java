package com.workfolio.workfolio_backend.member.oauth2.service.userInfo;

public interface OAuth2MemberInfo {
    String getProviderId();
    String getProvider();
    String getName();
    String getEmail();
}
