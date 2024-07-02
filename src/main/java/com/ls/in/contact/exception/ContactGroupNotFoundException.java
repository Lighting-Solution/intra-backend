package com.ls.in.contact.exception;

import com.ls.in.global.exception.BaseException;

public class ContactGroupNotFoundException extends BaseException {
    public ContactGroupNotFoundException(String message, int statusCode) {
        super(message, statusCode);
    }
}
