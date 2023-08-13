package ua.edu.khpi.project2023.exception;

public class EmailAlreadyExistException extends BadRequestException {

    private static final String DEFAULT_MESSAGE = "Email already registered";

    public EmailAlreadyExistException() {
        super(DEFAULT_MESSAGE);
    }

    public EmailAlreadyExistException(String message) {
        super(message);
    }
}
