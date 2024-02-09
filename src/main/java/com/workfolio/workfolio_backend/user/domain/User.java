package com.workfolio.workfolio_backend.user.domain;



import com.workfolio.workfolio_backend.user.enums.AuthProvider;
import com.workfolio.workfolio_backend.user.enums.Role;
import com.workfolio.workfolio_backend.user.config.oauth2.userInfo.OAuth2UserInfo;
import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    //nickname 나중에 수정 가능 (일단 null)
    private String name;

    private String oauth2Id;

    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User update(OAuth2UserInfo oAuth2UserInfo) {
        this.name = oAuth2UserInfo.getName();
        this.oauth2Id = oAuth2UserInfo.getOAuth2Id();

        return this;
    }


    //사용자 닉네임 변경
    public void updateNickname(String nickname) {
        this.name = nickname;
    }
}