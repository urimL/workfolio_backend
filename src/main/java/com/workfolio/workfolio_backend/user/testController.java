package com.workfolio.workfolio_backend.user;

import com.workfolio.workfolio_backend.user.domain.User;
import com.workfolio.workfolio_backend.user.repository.UserRepository;
import com.workfolio.workfolio_backend.user.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@Controller
@RequiredArgsConstructor
public class testController {

    private final CustomOAuth2UserService oAuth2MemberService;
    private final UserRepository memberRepository;

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@AuthenticationPrincipal User user) {
        return memberRepository.findById(user.getId()).orElseThrow(() -> new IllegalStateException("not found user"));
    }

    @GetMapping("/loginForm")
    public String login() {
        return "loginForm";
    }

}