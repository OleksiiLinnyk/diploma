package ua.edu.khpi.project2023.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistException extends BaseException {

    private static final HttpStatus NOT_ACCEPTABLE = HttpStatus.NOT_ACCEPTABLE;

    private static final String DEFAULT_MESSAGE = "";

    public UserAlreadyExistException() {
        super(NOT_ACCEPTABLE, DEFAULT_MESSAGE);
    }

    public UserAlreadyExistException(String message) {
        super(NOT_ACCEPTABLE, message);
    }
}
