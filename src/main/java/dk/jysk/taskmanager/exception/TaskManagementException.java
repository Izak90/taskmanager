package dk.jysk.taskmanager.exception;

import org.springframework.http.HttpStatus;

public class TaskManagementException extends BaseException {

    public TaskManagementException(String message) {
        super(message, "TASK_MANAGEMENT_ERROR", HttpStatus.BAD_REQUEST);
    }
}
