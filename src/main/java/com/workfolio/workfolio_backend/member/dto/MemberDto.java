package com.workfolio.workfolio_backend.member.dto;

import com.workfolio.workfolio_backend.member.domain.AuthProvider;
import com.workfolio.workfolio_backend.member.domain.Role;
import lombok.*;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MemberDto {
    private Long id;
    private String name;
    private String email;
    private Role role;
    private AuthProvider provider;
    private Timestamp createdAt;
    private String profile_img;
    private String refreshToken;

    @Builder
    public MemberDto(Long id, String name, String email, Role role, AuthProvider provider, String profile_img, Timestamp createdAt, String refreshToken) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.profile_img = profile_img;
        this.refreshToken = refreshToken;
    }

}
