package com.ls.in.contact.exception;

import com.ls.in.global.exception.BaseException;

public class PersonalContactNotFoundException extends BaseException {
    public PersonalContactNotFoundException(String message, int statusCode) {
        super(message, statusCode);
    }
}

