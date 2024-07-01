package com.ls.in.global.emp.exception;

import com.ls.in.global.exception.BaseException;

public class DepartmentNotFoundException extends BaseException {

    public DepartmentNotFoundException(String message, int statusCode) {
        super(message, statusCode);
    }
}
