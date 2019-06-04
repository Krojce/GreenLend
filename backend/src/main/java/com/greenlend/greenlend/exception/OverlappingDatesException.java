package com.greenlend.greenlend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class OverlappingDatesException extends RuntimeException {
    public OverlappingDatesException(String message) {
        super(message);
    }

    public OverlappingDatesException(String message, Throwable cause) {
        super(message, cause);
    }
}