package com.workfolio.workfolio_backend.Member.config.oauth.dto;

import com.workfolio.workfolio_backend.Member.domain.Member;
import com.workfolio.workfolio_backend.Member.domain.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String provider;

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if (registrationId.equals("naver")) {
            return ofNaver("id", attributes);
        }
        else if (registrationId.equals("kakao")) {
            return ofKakao("id", attributes);
        }

        return ofGoogle(userNameAttributeName, attributes);
}

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .provider("Google")
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();

    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> resp = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> account = (Map<String, Object>) resp.get("profile");

        return OAuthAttributes.builder()
                .name((String) account.get("nickname"))
                .email((String) resp.get("email"))
                .provider("Kakao")
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();

    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> resp = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .name((String) resp.get("name"))
                .email((String) resp.get("email"))
                .provider("Naver")
                .attributes(resp)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .email(email)
                .provider(provider)
                .role(Role.USER)
                .build();
    }
}
