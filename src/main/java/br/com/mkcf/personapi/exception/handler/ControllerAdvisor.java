package br.com.mkcf.personapi.exception.handler;

import br.com.mkcf.personapi.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(
                        LocalDateTime.now(),
                        ex.getMessage(),
                        request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(
            ResourceNotFoundException ex, WebRequest request) {

        ExceptionResponse exceptionResponse =
                new ExceptionResponse(
                        LocalDateTime.now(),
                        ex.getMessage(),
                        request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({MyFileNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(MyFileNotFoundException ex, WebRequest request) {

        ExceptionResponse exceptionResponse =
                new ExceptionResponse(
                        LocalDateTime.now(),
                        ex.getMessage(),
                        request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityUseException.class)
    public ResponseEntity<Object> handleEntityUseException(EntityUseException ex, WebRequest request) {

        ExceptionResponse exceptionResponse =
                new ExceptionResponse(
                        LocalDateTime.now(),
                        ex.getMessage(),
                        request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidJwtAuthenticationException.class)
    public ResponseEntity<Object> handleInvalidJwtAuthenticationException (InvalidJwtAuthenticationException ex, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(
                        LocalDateTime.now(),
                        ex.getMessage(),
                        request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }


}
