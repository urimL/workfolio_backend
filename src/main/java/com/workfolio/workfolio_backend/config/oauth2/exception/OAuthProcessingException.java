package com.workfolio.workfolio_backend.config.oauth2.exception;

public class OAuthProcessingException extends RuntimeException {

    public OAuthProcessingException(String message) {
        super(message);
    }
}