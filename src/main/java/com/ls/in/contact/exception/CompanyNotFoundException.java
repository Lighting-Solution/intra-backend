package com.ls.in.contact.exception;

import com.ls.in.global.exception.BaseException;

public class CompanyNotFoundException extends BaseException {
    public CompanyNotFoundException(String message, int statusCode) {
        super(message, statusCode);
    }
}
