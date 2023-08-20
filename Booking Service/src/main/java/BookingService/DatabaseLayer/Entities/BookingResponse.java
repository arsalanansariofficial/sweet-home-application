package BookingService.DatabaseLayer.Entities;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

/*
This class generates a response for the booking and returns the required response.
*/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingResponse {
    private int id;
    private String fromDate;
    private String toDate;
    private String aadharNumber;
    private StringBuilder roomNumbers = new StringBuilder();
    private int roomPrice;
    private int transactionId;
    private String bookedOn;

    /*
    This method generates a response for the booking and returns the required response.
    Parameter - BookingInfoEntity
    this takes the parameter BookingInfoEntity and creates a BookingResponse from this object.
    */

    public BookingResponse generateResponse(BookingInfoEntity bookingInfoEntity) {
        id = bookingInfoEntity.getBookingId();
        fromDate = bookingInfoEntity.getFromDate();
        toDate = bookingInfoEntity.getToDate();
        bookedOn = bookingInfoEntity.getBookedOn();
        aadharNumber = bookingInfoEntity.getAadharNumber();
        roomPrice = bookingInfoEntity.getRoomPrice();
        transactionId = bookingInfoEntity.getTransactionId();
        List<RoomNumbersEntity> roomNumbersEntities = bookingInfoEntity.getRoomNumberEntities();

        for (int i = 0; i < roomNumbersEntities.size(); i++) {
            roomNumbers = roomNumbers.append(roomNumbersEntities.get(i).getRoomNumber());
            if (i != roomNumbersEntities.size() - 1)
                roomNumbers.append(", ");
        }

        BookingResponse bookingResponse = new BookingResponse();
        bookingResponse.setId(id);
        bookingResponse.setFromDate(fromDate);
        bookingResponse.setToDate(toDate);
        bookingResponse.setAadharNumber(aadharNumber);
        bookingResponse.setRoomNumbers(roomNumbers);
        bookingResponse.setRoomPrice(roomPrice);
        bookingResponse.setTransactionId(transactionId);
        bookingResponse.setBookedOn(bookedOn);

        return bookingResponse;
    }

    /*
    This method prints the object in the form of a string.
    */

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                ", aadharNumber='" + aadharNumber + '\'' +
                ", roomNumbers='" + roomNumbers +
                "' , roomPrice=" + roomPrice +
                ", transactionId=" + transactionId +
                ", bookedOn='" + bookedOn + '\'' +
                '}';
    }
}