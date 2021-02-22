package br.com.mkcf.personapi.exception;

public class NoDataFoundException extends RuntimeException {
    public NoDataFoundException(String exception) {
        super("No data found");
    }
}
