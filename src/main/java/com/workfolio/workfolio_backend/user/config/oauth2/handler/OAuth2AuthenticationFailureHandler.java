package com.workfolio.workfolio_backend.user.config.oauth2.handler;

import com.workfolio.workfolio_backend.user.repository.CookieAuthorizationRequestRepository;
import com.workfolio.workfolio_backend.user.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.Cookie;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

import static com.workfolio.workfolio_backend.user.repository.CookieAuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final CookieAuthorizationRequestRepository cookieAuthorizationRequestRepository;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException {
        String targetUrl = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue)
                .orElse("/");

        System.out.println("failure targetUrl : " + targetUrl);

        targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("error", authenticationException.getLocalizedMessage())
                .build().toUriString();

        cookieAuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}