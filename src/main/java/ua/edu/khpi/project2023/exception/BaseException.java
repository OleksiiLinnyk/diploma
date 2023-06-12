package ua.edu.khpi.project2023.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BaseException extends ResponseStatusException {

    private static final String DEFAULT_MESSAGE = "General exception message";
    private static final HttpStatus HTTP_STATUS_500 = HttpStatus.INTERNAL_SERVER_ERROR;

    public BaseException() {
        super(HTTP_STATUS_500, DEFAULT_MESSAGE);
    }

    public BaseException(HttpStatus status) {
        super(status);
    }

    public BaseException(HttpStatus status, String reason) {
        super(status, reason);
    }
}
