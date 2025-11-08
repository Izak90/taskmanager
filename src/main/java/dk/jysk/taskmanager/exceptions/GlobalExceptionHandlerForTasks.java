package dk.jysk.taskmanager.exceptions;

import dk.jysk.taskmanager.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandlerForTasks {

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTaskNotFoundException(TaskNotFoundException ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(ex.getErrorCode(),ex.getMessage(),ex.getStatus().value(), Instant.now(), request.getRequestURI());
        return new ResponseEntity<>(error, ex.getStatus());
    }

    @ExceptionHandler(CreatingTaskException.class)
    public ResponseEntity<ErrorResponse> handleCreatingTaskException(CreatingTaskException ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(ex.getErrorCode(),ex.getMessage(),ex.getStatus().value(), Instant.now(), request.getRequestURI());
        return new ResponseEntity<>(error, ex.getStatus());
    }
}
