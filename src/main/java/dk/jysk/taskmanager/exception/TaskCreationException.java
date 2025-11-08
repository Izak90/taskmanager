package dk.jysk.taskmanager.exception;

import org.springframework.http.HttpStatus;

public class TaskCreationException extends BaseException {

    public TaskCreationException(String message) {
        super(message, "TASK_CREATION_ERROR", HttpStatus.BAD_REQUEST);
    }
}
