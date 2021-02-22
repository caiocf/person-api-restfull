package br.com.mkcf.personapi.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidJwtAuthenticationException extends AuthenticationException {
    public InvalidJwtAuthenticationException(String exception) {
        super(exception);
    }
}
