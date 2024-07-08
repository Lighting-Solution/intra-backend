package com.ls.in.contact.exception;

import com.ls.in.global.exception.BaseException;

public class PersonalGroupNotFoundException extends BaseException {
    public PersonalGroupNotFoundException(String message, int statusCode) {
        super(message, statusCode);
    }
}
