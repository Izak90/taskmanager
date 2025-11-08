package dk.jysk.taskmanager.exceptions;

import org.springframework.http.HttpStatus;

public class TaskNotFoundException extends BaseException {
    public TaskNotFoundException() {
    }

    public TaskNotFoundException(String message, String errorCode, HttpStatus status) {
        super(message, errorCode, status);
    }
}
