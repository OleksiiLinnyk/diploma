package ua.edu.khpi.project2023.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends BaseException {

    private static final HttpStatus BAD_REQUEST = HttpStatus.BAD_REQUEST;
    private static final String DEFAULT_MESSAGE = "Bad request";

    public BadRequestException() {
        super(BAD_REQUEST, DEFAULT_MESSAGE);
    }

    public BadRequestException(String message) {
        super(BAD_REQUEST, message);
    }
}
