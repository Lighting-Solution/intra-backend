package com.ls.in.global.emp.exception;

import com.ls.in.global.exception.BaseException;

public class EmpNotFoundException extends BaseException {

    public EmpNotFoundException(String message, int statusCode) {
        super(message, statusCode);
    }
}
