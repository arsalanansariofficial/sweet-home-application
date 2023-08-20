package BookingService.ServiceLayer.ServiceInterfaces;
import BookingService.DatabaseLayer.Entities.BookingInfoEntity;
import BookingService.DatabaseLayer.Entities.Transaction;

/*
This interface provides two methods to perform booking and payment operations.
 */

public interface BookingServiceInterface {
    BookingInfoEntity bookRoomsInHotel(BookingInfoEntity bookingInfoEntity) throws Exception;
    BookingInfoEntity createPayment(Transaction transaction) throws Exception;
}