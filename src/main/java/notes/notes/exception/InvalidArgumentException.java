package notes.notes.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

public class InvalidArgumentException extends Exception {
    private final String message;

    @Setter
    @Getter
    private HttpStatus statusCode = HttpStatus.BAD_REQUEST;

    public InvalidArgumentException(String message) {
        super(message);
        this.message = message;
    }

    public InvalidArgumentException(String message, HttpStatus statusCode) {
        super(message);
        this.message = message;
        this.statusCode = statusCode;
    }
}
