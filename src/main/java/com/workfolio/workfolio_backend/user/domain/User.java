package com.workfolio.workfolio_backend.user.domain;



import com.workfolio.workfolio_backend.user.enums.AuthProvider;
import com.workfolio.workfolio_backend.user.enums.Role;
import com.workfolio.workfolio_backend.user.config.oauth2.userInfo.OAuth2UserInfo;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String name;

    //nickname 나중에 수정 가능 (일단 name이랑 같게)
    private String nickname;

    private String oauth2Id;

    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public User(String email, String name, String nickname, String oauth2Id, AuthProvider authProvider, Role role) {
        this.email = email;
        this.name = name;
        this.nickname = name;
        this.oauth2Id = oauth2Id;
        this.authProvider = authProvider;
        this.role = role;
    }

    public User update(OAuth2UserInfo oAuth2UserInfo) {
        this.name = oAuth2UserInfo.getName();
        this.oauth2Id = oAuth2UserInfo.getOAuth2Id();

        return this;
    }

    //사용자 닉네임 변경
    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }
}