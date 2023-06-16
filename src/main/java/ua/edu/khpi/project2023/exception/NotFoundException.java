package ua.edu.khpi.project2023.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseException {

    private static final HttpStatus NOT_FOUND = HttpStatus.NOT_FOUND;

    private static final String DEFAULT_MESSAGE = "Resource not found";

    public NotFoundException() {
        super(NOT_FOUND, DEFAULT_MESSAGE);
    }

    public NotFoundException(String reason) {
        super(NOT_FOUND, reason);
    }
}
