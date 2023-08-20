package PaymentService.ExceptionHandling;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/*
This class handles all the exceptions that are thrown in any class or method in the whole package.
 */

@ControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorResponse response = new ErrorResponse(e.getMessage(), 404);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
