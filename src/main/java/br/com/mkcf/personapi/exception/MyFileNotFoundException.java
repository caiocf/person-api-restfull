package br.com.mkcf.personapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class MyFileNotFoundException extends RuntimeException {

    public MyFileNotFoundException(String exception) {
        super(exception);
    }

    public MyFileNotFoundException(String exception, Throwable cause) {
        super(exception, cause);
    }
}
