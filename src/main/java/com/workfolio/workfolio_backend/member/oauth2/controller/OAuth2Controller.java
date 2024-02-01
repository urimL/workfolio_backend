package com.workfolio.workfolio_backend.member.oauth2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OAuth2Controller {
    @GetMapping("/loginform")
    public String home() {
        return "loginForm";
    }
}
