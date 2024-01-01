package notes.notes.exception.handler;

import notes.notes.exception.InvalidArgumentException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InvalidArgumentExceptionHandler {
    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseEntity<?> handleInvalidArgumentException(InvalidArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), ex.getStatusCode());
    }
}
