package com.workfolio.workfolio_backend.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {

    ROLE_GUEST("게스트"), ROLE_USER("사용자");

    private String description;

}