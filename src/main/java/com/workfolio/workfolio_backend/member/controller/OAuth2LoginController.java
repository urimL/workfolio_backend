package com.workfolio.workfolio_backend.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OAuth2LoginController {
    @GetMapping("/loginForm")
    public String home() {
        return "loginForm";
    }
}
