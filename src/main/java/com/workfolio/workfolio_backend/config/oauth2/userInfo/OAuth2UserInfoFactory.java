package com.workfolio.workfolio_backend.config.oauth2.userInfo;

import com.workfolio.workfolio_backend.member.domain.AuthProvider;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2MemberInfo getOAuth2UserInfo(AuthProvider authProvider, Map<String, Object> attributes) {
        switch (authProvider) {
            case GOOGLE : return new GoogleMemberInfo(attributes);
            case NAVER: return new NaverMemberInfo(attributes);
            case KAKAO: return new KakaoMemberInfo(attributes);
            default: throw new IllegalArgumentException("Unexpected Provider Type");
        }
    }
}
