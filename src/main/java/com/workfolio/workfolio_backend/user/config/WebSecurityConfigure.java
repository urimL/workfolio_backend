package com.workfolio.workfolio_backend.user.config;

import com.workfolio.workfolio_backend.user.config.jwt.JwtAuthenticationFilter;
import com.workfolio.workfolio_backend.user.config.jwt.JwtTokenProvider;
import com.workfolio.workfolio_backend.user.config.oauth2.handler.OAuth2AuthenticationFailureHandler;
import com.workfolio.workfolio_backend.user.config.oauth2.handler.OAuth2AuthenticationSuccessHandler;
import com.workfolio.workfolio_backend.user.repository.CookieAuthorizationRequestRepository;
import com.workfolio.workfolio_backend.user.service.CustomOAuth2UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfigure {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final JwtTokenProvider jwtTokenProvider;
    private final CookieAuthorizationRequestRepository cookieAuthorizationRequestRepository;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .httpBasic().disable()
                .csrf().disable()
                .rememberMe().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .requestMatchers("/oauth2/**","/","/auth/**").permitAll()
                .requestMatchers("/success","/api/v1/**").hasRole("USER");

        http.oauth2Login()
                .authorizationEndpoint().baseUri("/oauth2/authorization")  // 소셜 로그인 url
                .authorizationRequestRepository(cookieAuthorizationRequestRepository)  // 인증 요청을 cookie 에 저장
                .and()
                .redirectionEndpoint().baseUri("/oauth2/code/*")  // 소셜 인증 후 redirect url
                .and()
                .userInfoEndpoint().userService(customOAuth2UserService)  // 회원 정보 처리
                .and()
                .successHandler(oAuth2AuthenticationSuccessHandler)
                .failureHandler(oAuth2AuthenticationFailureHandler);

        http.logout()
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID");

        //jwt filter 설정
        http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

