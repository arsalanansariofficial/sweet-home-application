package BookingService.DatabaseLayer.Entities;
import lombok.Getter;
import lombok.Setter;

/*
This class makes a request body to complete the payment of the booking.
*/

@Getter
@Setter
public class Transaction {
    private int transactionId;
    private int bookingId;
    private String paymentMode;
    private String upiId;
    private String cardNumber;
}