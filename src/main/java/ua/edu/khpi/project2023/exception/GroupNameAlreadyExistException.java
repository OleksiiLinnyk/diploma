package ua.edu.khpi.project2023.exception;

import org.springframework.http.HttpStatus;

public class GroupNameAlreadyExistException extends BaseException {

    private static final HttpStatus BAD_REQUEST = HttpStatus.BAD_REQUEST;

    private static final String DEFAULT_MESSAGE = "Group already exist";

    public GroupNameAlreadyExistException() {
        super(BAD_REQUEST, DEFAULT_MESSAGE);
    }

    public GroupNameAlreadyExistException(String reason) {
        super(BAD_REQUEST, reason);
    }
}
