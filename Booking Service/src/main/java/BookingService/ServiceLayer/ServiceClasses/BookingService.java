package BookingService.ServiceLayer.ServiceClasses;
import BookingService.DatabaseLayer.Entities.BookingInfoEntity;
import BookingService.DatabaseLayer.Entities.RoomNumbersEntity;
import BookingService.DatabaseLayer.Entities.Transaction;
import BookingService.DatabaseLayer.Repositories.BookingInfoRepository;
import BookingService.DatabaseLayer.Repositories.RoomNumbersRepository;
import BookingService.ServiceLayer.ServiceInterfaces.BookingServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/*
This class is actually contains the business logic to perform the booking and payment task.
 */

@Service
public class BookingService implements BookingServiceInterface {
    BookingInfoRepository bookingInfoRepository;
    RoomNumbersRepository roomNumbersRepository;
    RestTemplate restTemplate;

    @Value("${transaction.url}")
    String transactionURL;

    @Autowired
    public BookingService(BookingInfoRepository bookingInfoRepository, RoomNumbersRepository roomNumbersRepository, RestTemplate restTemplate) {
        this.bookingInfoRepository = bookingInfoRepository;
        this.roomNumbersRepository = roomNumbersRepository;
        this.restTemplate = restTemplate;
    }

    /* This method books rooms in the hotel and generates a unique booking id corresponding to the requirement.
    Parameter - BookingInfoEntity
     */

    @Override
    public BookingInfoEntity bookRoomsInHotel(BookingInfoEntity bookingInfoEntity) throws Exception {
        List<String> allRoomNumbers = getAllRoomNumbers();

        if (allRoomNumbers.size() == 1000) throw new Exception("Rooms not available");
        else if (bookingInfoEntity.getNumOfRooms() <= 0) throw new Exception("At-Least 1 room must be selected");
        else if (bookingInfoEntity.getNumOfRooms() > (1000 - allRoomNumbers.size())) throw new Exception("Requested rooms are not available, Available Rooms are: " + (1000 - allRoomNumbers.size()));

        List<String> allottedRoomNumbers = allotRoomNumbersForCurrentBooking(bookingInfoEntity.getNumOfRooms(), allRoomNumbers);
        bookingInfoEntity.setRoomPrice(1000 * bookingInfoEntity.getNumOfRooms() * (getLocalDateFromString(bookingInfoEntity.getToDate()).getDayOfMonth() - getLocalDateFromString(bookingInfoEntity.getFromDate()).getDayOfMonth()));
        BookingInfoEntity savedBooking = bookingInfoRepository.save(formatBooking(bookingInfoEntity));
        for (String i: allottedRoomNumbers) {
            RoomNumbersEntity roomNumbersEntity = new RoomNumbersEntity();
            roomNumbersEntity.setRoomNumber(i);
            roomNumbersEntity.setBookingInfoEntity(savedBooking);
            roomNumbersRepository.save(roomNumbersEntity);
        }
        savedBooking.setRoomNumberEntities(getAllottedRoomNumbersEntities(savedBooking.getBookingId()));
        return bookingInfoRepository.save(savedBooking);
    }

    /* This method interacts with the payment service and update the transaction id
    corresponding to the booking id in the database if the payment is successful.
    Parameter - Transaction
     */

    @Override
    public BookingInfoEntity createPayment(Transaction transaction) throws Exception {
        BookingInfoEntity savedBooking = bookingInfoRepository.findById(transaction.getBookingId()).orElse(null);
        if (savedBooking != null) {
            if (transaction.getPaymentMode().equals("UPI") || transaction.getPaymentMode().equals("CARD")) {
                HttpEntity<Transaction> httpEntity = new HttpEntity<>(transaction);
                Integer receivedTransactionId = restTemplate.postForObject(transactionURL, httpEntity, Integer.class);
                if (receivedTransactionId != null) {
                    savedBooking.setTransactionId(receivedTransactionId);
                    bookingInfoRepository.save(savedBooking);
                    return savedBooking;
                }
                else throw new Exception("Transaction Failed");
            }
            else throw new Exception("Invalid mode of payment");
        }
        else throw new Exception("Invalid Booking Id");
    }

    /* This method returns the list of all room numbers that are allotted to the user while booking the rooms.
    Parameter - Null
     */

    public List<String> getAllRoomNumbers() {
        List<String> allRoomNumbers = new ArrayList<>();
        List<RoomNumbersEntity> roomEntities = roomNumbersRepository.findAll();
        for (RoomNumbersEntity i: roomEntities) {
            allRoomNumbers.add(i.getRoomNumber());
        }
        return allRoomNumbers;
    }

    /* This method returns the list of allotted room_numbers_list entities, that are allotted to the user while booking.
    Parameter - bookingId
     */

    public List<RoomNumbersEntity> getAllottedRoomNumbersEntities(int bookingId) {
        List<RoomNumbersEntity> allRoomNumbersEntities = roomNumbersRepository.findAll();
        List<RoomNumbersEntity> allottedRoomNumbersEntities = new ArrayList<>();
        if (!allRoomNumbersEntities.isEmpty()) {
            for (RoomNumbersEntity i: allRoomNumbersEntities) {
                if (i.getBookingInfoEntity().getBookingId() == bookingId)
                    allottedRoomNumbersEntities.add(i);
            }
        }
        return allottedRoomNumbersEntities;
    }

    /* This method generates unique room number for the requested rooms, and it also makes sure that the room is available
    for booking and no two people have the same room number. It might be problem if two person are allotted with the same
    roo number. This method returns the list of the room numbers that are generated.
    Parameter - numberOfRooms, allRoomNumbers
     */

    public List<String> allotRoomNumbersForCurrentBooking(int numberOfRooms, List<String> allRoomNumbers) {
        Random rand = new Random();
        List<String> allottedRoomNumbers = new ArrayList<>();
        for (int i = 0; i < numberOfRooms; i++) {
            String roomNumber = String.valueOf(rand.nextInt(1000));
            while (allRoomNumbers.contains(roomNumber)) {
                roomNumber = String.valueOf(rand.nextInt(1000));
            }
            allottedRoomNumbers.add(roomNumber);
            allRoomNumbers.add(roomNumber);
        }
        return allottedRoomNumbers;
    }

    /* This method returns the LocalDate object for the provided string input.
    Parameter - date
     */

    public LocalDate getLocalDateFromString(String date) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, dateFormat);
    }

    /* This method generate a required format of booking to be shown to the user and returns bookingInfoEntity object.
    Parameter - BookingInfoEntity
     */

    public BookingInfoEntity formatBooking(BookingInfoEntity bookingInfoEntity) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        bookingInfoEntity.setFromDate(bookingInfoEntity.getFromDate() + "T00:00:00.000+00:00");
        bookingInfoEntity.setToDate(bookingInfoEntity.getToDate() + "T00:00:00.000+00:00");
        bookingInfoEntity.setBookedOn(simpleDateFormat.format(timestamp));
        return bookingInfoEntity;
    }
}