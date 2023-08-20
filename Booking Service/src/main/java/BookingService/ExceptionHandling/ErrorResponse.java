package BookingService.ExceptionHandling;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
This class object is used to provide an error message and status code is any.
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ErrorResponse {
    private String errorMessage;
    private int statusCode;
}