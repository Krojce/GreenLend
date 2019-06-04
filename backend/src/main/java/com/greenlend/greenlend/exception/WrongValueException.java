package com.greenlend.greenlend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class WrongValueException extends RuntimeException {
    public WrongValueException(String message) {
        super(message);
    }

    public WrongValueException(String message, Throwable cause) {
        super(message, cause);
    }
}
