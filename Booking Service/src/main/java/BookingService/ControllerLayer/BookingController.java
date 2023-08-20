package BookingService.ControllerLayer;
import BookingService.DatabaseLayer.Entities.BookingInfoEntity;
import BookingService.DatabaseLayer.Entities.BookingResponse;
import BookingService.DatabaseLayer.Entities.Transaction;
import BookingService.ServiceLayer.ServiceClasses.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
This is the Controller class which maps the request with the specific URL to the specific classes.
 */

@RestController
@RequestMapping(value = "/hotel")
public class BookingController {
    BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    /*
    This method books rooms in the hotel required by the user.
    */

    @PostMapping(value = "/booking")
    public ResponseEntity<BookingResponse> bookRoomsInHotel(@RequestBody BookingInfoEntity bookingInfoEntity) throws Exception {
        BookingInfoEntity savedBooking = bookingService.bookRoomsInHotel(bookingInfoEntity);
        BookingResponse bookingResponse = new BookingResponse();
        return new ResponseEntity<>(bookingResponse.generateResponse(savedBooking), HttpStatus.CREATED);
    }

    /*
    This method makes payment corresponding to booking id.
    */

    @PostMapping(value = "/booking/{bookingId}/transaction")
    public ResponseEntity<BookingResponse> createPayment(@RequestBody Transaction transaction) throws Exception {
        BookingResponse savedBooking = new BookingResponse();
        savedBooking = savedBooking.generateResponse(bookingService.createPayment(transaction));
        printConfirmationMessage(savedBooking);
        return new ResponseEntity<>(savedBooking, HttpStatus.CREATED);
    }

    /*
    This method prints the success message if the payment is successful.
    */

    public void printConfirmationMessage(BookingResponse savedBooking) {
        System.out.println("Booking confirmed for user with aadhaar number: " + savedBooking.getAadharNumber() + "    |    " + "Here are the booking details: " + savedBooking);
    }
}
