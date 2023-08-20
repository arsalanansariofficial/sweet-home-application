package BookingService.DatabaseLayer.Entities;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;

/*
This class corresponds to the booking table in the database.
*/

@Entity
@Table(name = "booking")
@Getter
@Setter
public class BookingInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private int bookingId;

    @Column(name = "from_date")
    private String fromDate = null;

    @Column(name = "to_date")
    private String toDate = null;

    @Column(name = "booked_on")
    private String bookedOn;

    @Column(name = "aadhar_number", length = 12)
    private String aadharNumber = null;

    @Column(name = "num_of_rooms", length = 5, nullable = false)
    private int numOfRooms;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "bookingInfoEntity")
    private List<RoomNumbersEntity> roomNumberEntities;

    @Column(name = "room_price", length = 10, nullable = false)
    private int roomPrice;

    @Column(name = "transaction_id", length = 50, nullable = false)
    private int transactionId = 0;
}