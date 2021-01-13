package com.recruitment.project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CredentialsTakenException extends RuntimeException{
    public CredentialsTakenException(String msg) {
        super(msg);
    }
}
