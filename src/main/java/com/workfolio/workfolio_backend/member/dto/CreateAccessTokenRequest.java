package com.workfolio.workfolio_backend.member.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CreateAccessTokenRequest {
    private String refreshToken;
}