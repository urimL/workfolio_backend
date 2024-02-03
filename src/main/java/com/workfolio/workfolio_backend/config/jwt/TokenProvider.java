package com.workfolio.workfolio_backend.config.jwt;

import com.workfolio.workfolio_backend.member.domain.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class TokenProvider {

    private final JwtProperties jwtProperties;

    public String generateToken(Member member, Duration expiredAt) {
        Date now = new Date();
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), member);
    }

    //create token
    private String makeToken(Date expiry, Member member) {
        Date now = new Date();

        return Jwts.builder()
                //헤더 타입 : JWT
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                //내용 iss(발급자) : properties 파일에서 설정한 값
                .setIssuer(jwtProperties.getIssuer())
                //내용 iat(발급 일시) : 현재 시간
                .setIssuedAt(now)
                //내용 exp(만료 일시) : expiry 멤버 변숫값
                .setExpiration(expiry)
                //내용 sub(토큰 제목) : 유저 이메일
                .setSubject(member.getEmail())
                //클레임 id : 유저 ID
                .claim("id", member.getId())
                //서명 : 비밀값과 함께 해시값을 HS256 방식으로 암호화
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    //token validation
    public boolean validToken(String token) {
        try {
            Jwts.parser()
                    //비밀값으로 복호화
                    .setSigningKey(jwtProperties.getSecretKey())
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            //복호화 과정에서 에러가 나면 유효하지 않은 토큰
            return false;
        }

    }

    //get validation information
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

        return new UsernamePasswordAuthenticationToken(new org.springframework.security.core.userdetails
                .User(claims.getSubject(), "", authorities), token, authorities);
    }


    public Long getUserId(String token) {
        Claims claims = getClaims(token);
        return claims.get("id", Long.class);
    }

    //properties의 비밀값으로 토큰 복호화 -> 사용자에 대한 속성인 claims get
    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }

}