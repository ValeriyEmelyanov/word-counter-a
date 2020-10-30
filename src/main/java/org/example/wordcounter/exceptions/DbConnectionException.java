package org.example.wordcounter.exceptions;

public class DbConnectionException extends RuntimeException {
    public DbConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
