package ru.geekbrains.backend.security.exception;

import org.springframework.security.core.AuthenticationException;

public class UserExistsException extends AuthenticationException {
    public UserExistsException(String message) {
        super(message);
    }

    public UserExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
