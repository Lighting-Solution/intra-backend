package com.ls.in.global.exception;


public class BaseException extends RuntimeException {
    private final int statusCode;

    public BaseException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
