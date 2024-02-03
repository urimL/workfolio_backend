package com.workfolio.workfolio_backend.config.jwt;

import com.workfolio.workfolio_backend.config.oauth2.PrincipalDetails;
import com.workfolio.workfolio_backend.member.repository.MemberRepository;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Service
public class TokenProvider {

    private final String SECRET_KEY;
    private final String COOKIE_REFRESH_TOKEN_KEY;
    private final Long ACCESS_TOKEN_EXPIRE_LENGTH = 1000L * 60 * 60;        // 1hour
    private final Long REFRESH_TOKEN_EXPIRE_LENGTH = 1000L * 60 * 60 * 24 * 7;    // 1week
    private final String AUTHORITIES_KEY = "role";
    private final String ISSUER;

    @Autowired
    private MemberRepository memberRepository;

    public TokenProvider(@Value("${jwt.issuer}") String issuer, @Value("${jwt.secret_key}") String secretKey, @Value("${jwt.refresh_cookie_key}") String cookieKey) {
        this.SECRET_KEY = Base64.getEncoder().encodeToString(secretKey.getBytes());
        this.COOKIE_REFRESH_TOKEN_KEY = cookieKey;
        this.ISSUER = issuer;
    }

    public String createAccessToken(Authentication authentication) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_LENGTH);

        PrincipalDetails member = (PrincipalDetails) authentication.getPrincipal();

        String memberId = member.getName();
        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setSubject(memberId)
                .claim(AUTHORITIES_KEY, role)
                .setIssuer(ISSUER)
                .setIssuedAt(now)
                .setExpiration(validity)
                .compact();
    }

    public void createRefreshToken(Authentication authentication, HttpServletResponse resp) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_LENGTH);

        String refreshToken = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .setIssuer(ISSUER)
                .setIssuedAt(now)
                .setExpiration(validity)
                .compact();

        saveRefreshToken(authentication, refreshToken);

        ResponseCookie cookie = ResponseCookie.from(COOKIE_REFRESH_TOKEN_KEY, refreshToken)
                .httpOnly(true)
                .secure(true)
                .sameSite("Lax")
                .maxAge(REFRESH_TOKEN_EXPIRE_LENGTH / 1000)
                .path("/")
                .build();

        resp.addHeader("Set-Cookie", cookie.toString());
    }

    private void saveRefreshToken(Authentication authentication, String refreshToken) {
        PrincipalDetails member = (PrincipalDetails) authentication.getPrincipal();
        Long id = Long.valueOf(member.getName());

        memberRepository.updateRefreshToken(id, refreshToken);
    }

    //accesstoken 검사 + 얻은 정보로 authentication 객체 생성
    public Authentication getAuthentication(String accessToekn) {
        Claims claims = parseClaims(accessToekn);

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        PrincipalDetails principal = new PrincipalDetails(Long.valueOf(claims.getSubject()), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    private Claims parseClaims(String accessToekn) {
        try {
            return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(accessToekn).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    //token validation
    public boolean validToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalStateException e) {
            log.info("JWT 토큰이 잘못되었습니다");
        }
        return false;
    }


}