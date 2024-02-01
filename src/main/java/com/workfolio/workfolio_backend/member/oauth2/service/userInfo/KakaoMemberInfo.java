package com.workfolio.workfolio_backend.member.oauth2.service.userInfo;

import java.util.Map;

public class KakaoMemberInfo implements OAuth2MemberInfo {
    public KakaoMemberInfo(Map<String, Object> attributes) {
    }

    @Override
    public String getProviderId() {
        return null;
    }

    @Override
    public String getProvider() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getEmail() {
        return null;
    }
}
