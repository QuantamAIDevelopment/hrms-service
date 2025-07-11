package com.qaid.hrms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class ExpiredTokenException extends RuntimeException {
    public ExpiredTokenException() {
        super("The authentication token has expired");
    }
}