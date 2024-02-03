package com.workfolio.workfolio_backend.member.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor

public enum Role {
    GUEST("ROLE_GUEST","guest"),
    USER("ROLE_USER","user");

    private final String role;
    private final String name;
}
