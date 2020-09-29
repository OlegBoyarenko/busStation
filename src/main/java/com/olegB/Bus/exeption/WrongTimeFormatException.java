package com.olegB.Bus.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception, throw if time invalid
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Time parse exception")
public class WrongTimeFormatException extends RuntimeException {
    public WrongTimeFormatException(String message) {
        super(message);
    }
}
