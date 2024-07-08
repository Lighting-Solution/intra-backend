package com.ls.in.global.emp.exception;

import com.ls.in.global.exception.BaseException;

public class PositionNotFoundException extends BaseException {

    public PositionNotFoundException(String message, int statusCode) {
        super(message, statusCode);
    }
}
