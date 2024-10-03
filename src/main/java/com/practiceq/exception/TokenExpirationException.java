package com.practiceq.exception;

public class TokenExpirationException extends RuntimeException {
    public TokenExpirationException(String tokenExpired) {
        super(tokenExpired);
    }
}
