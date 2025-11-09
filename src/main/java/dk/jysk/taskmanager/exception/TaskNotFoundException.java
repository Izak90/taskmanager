package dk.jysk.taskmanager.exception;

import org.springframework.http.HttpStatus;

public class TaskNotFoundException extends BaseException {

    public TaskNotFoundException(String message) {
        super(message, "TASK_NOT_FOUND", HttpStatus.NOT_FOUND);
    }
}
