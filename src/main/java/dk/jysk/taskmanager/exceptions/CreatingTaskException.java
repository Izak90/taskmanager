package dk.jysk.taskmanager.exceptions;

import org.springframework.http.HttpStatus;

public class CreatingTaskException extends BaseException {
    public CreatingTaskException() {
    }

    public CreatingTaskException(String message, String errorCode, HttpStatus status) {
        super(message, errorCode, status);
    }
}
