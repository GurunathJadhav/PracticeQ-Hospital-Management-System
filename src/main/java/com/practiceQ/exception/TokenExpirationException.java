package com.practiceQ.exception;

public class TokenExpirationException extends RuntimeException {
    public TokenExpirationException(String tokenExpired) {
        super(tokenExpired);
    }
}
