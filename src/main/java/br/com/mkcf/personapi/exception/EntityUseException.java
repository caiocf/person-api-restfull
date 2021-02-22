package br.com.mkcf.personapi.exception;

public class EntityUseException extends RuntimeException {
    public EntityUseException(String exception) {
        super(exception);
    }
}
