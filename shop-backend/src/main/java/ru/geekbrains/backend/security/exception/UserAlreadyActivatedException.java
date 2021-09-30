package ru.geekbrains.backend.security.exception;

import org.springframework.security.core.AuthenticationException;

public class UserAlreadyActivatedException extends AuthenticationException {
    public UserAlreadyActivatedException(String message) {
        super(message);
    }

    public UserAlreadyActivatedException(String message, Throwable cause) {
        super(message, cause);
    }
}
