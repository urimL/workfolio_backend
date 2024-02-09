package com.workfolio.workfolio_backend.user.dto;

import com.workfolio.workfolio_backend.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserViewResponse {

    private Long id;
    private String name;
    private String email;

    public UserViewResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
