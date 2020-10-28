package org.example.wordcounter.exceptions;

public class DownloadException extends RuntimeException {
    public DownloadException(Throwable cause) {
        super(cause);
    }
}
