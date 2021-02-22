package br.com.mkcf.personapi.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String exception) {
        super(exception);
    }
}
