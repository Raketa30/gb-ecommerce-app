package ru.geekbrains.backend.security.exception;

import org.springframework.security.core.AuthenticationException;

public class RoleNotFoundException extends AuthenticationException {

    public RoleNotFoundException(String message) {
        super(message);
    }

    public RoleNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
