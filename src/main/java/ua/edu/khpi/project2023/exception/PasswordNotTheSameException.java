package ua.edu.khpi.project2023.exception;

public class PasswordNotTheSameException extends BadRequestException {

    private static final String DEFAULT_MESSAGE = "Passwords are not the same";

    public PasswordNotTheSameException() {
        super(DEFAULT_MESSAGE);
    }

    public PasswordNotTheSameException(String message) {
        super(message);
    }
}
