package com.workfolio.workfolio_backend.member.oauth2.service;

import com.workfolio.workfolio_backend.member.entity.Member;
import com.workfolio.workfolio_backend.member.oauth2.entity.PrincipalDetails;
import com.workfolio.workfolio_backend.member.oauth2.service.userInfo.GoogleMemberInfo;
import com.workfolio.workfolio_backend.member.oauth2.service.userInfo.KakaoMemberInfo;
import com.workfolio.workfolio_backend.member.oauth2.service.userInfo.NaverMemberInfo;
import com.workfolio.workfolio_backend.member.oauth2.service.userInfo.OAuth2MemberInfo;
import com.workfolio.workfolio_backend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuth2MemberService extends DefaultOAuth2UserService {
    private final BCryptPasswordEncoder encoder;
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuth2MemberInfo memberInfo = null;
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        if (registrationId.equals("google")) {
            memberInfo = new GoogleMemberInfo(oAuth2User.getAttributes());
        } else if (registrationId.equals("kakao")) {
            memberInfo = new KakaoMemberInfo((Map)oAuth2User.getAttributes().get("response"));
        } else if (registrationId.equals("naver")) {
            memberInfo = new NaverMemberInfo((Map)oAuth2User.getAttributes().get("response"));
        } else {
            System.out.println("Login Failed : Unexpected Social Login Provider");
        }

        String provider = memberInfo.getProvider();
        String providerId = memberInfo.getProviderId();
        String username = provider + "_" + providerId; //중복이 발생하지 않도록 provider와 providerId를 조합
        String email = memberInfo.getEmail();
        String role = "ROLE_ADMIN"; //일반 유저
        System.out.println(oAuth2User.getAttributes());

        Optional<Member> findMember = memberRepository.findByName(username);
        Member member=null;

        if (findMember.isEmpty()) {
            member = Member.builder()
                    .name(username)
                    .email(email)
                    .password(encoder.encode("password"))
                    .role(role)
                    .provider(provider)
                    .provider_id(providerId).build();
            memberRepository.save(member);
        }
        else{
            member=findMember.get();
        }
        return new PrincipalDetails(member, oAuth2User.getAttributes());
    }
}