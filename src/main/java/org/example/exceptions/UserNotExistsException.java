package org.example.exceptions;

public class UserNotExistsException extends Exception {

    public UserNotExistsException() {
        super("User does not exist");
    }

    public UserNotExistsException(String message) {
        super(message);
    }

    public UserNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}