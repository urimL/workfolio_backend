package com.workfolio.workfolio_backend.member.controller;

import com.workfolio.workfolio_backend.config.oauth2.PrincipalDetails;
import com.workfolio.workfolio_backend.config.oauth2.service.OAuth2MemberService;
import com.workfolio.workfolio_backend.member.domain.Member;
import com.workfolio.workfolio_backend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Log4j2
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final OAuth2MemberService oAuth2MemberService;
    private final MemberRepository memberRepository;

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public Member getCurrentUser(@AuthenticationPrincipal PrincipalDetails user) {
        return memberRepository.findById(user.getId()).orElseThrow(() -> new IllegalStateException("not found user"));
    }

    @GetMapping("/loginForm")
    public String login() {
        return "loginForm";
    }
}
