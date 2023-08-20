package BookingService.DatabaseLayer.Entities;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

/*
This class corresponds to room_numbers_list in the database and stores all the rooms numbers that are allotted to
the user with a specific booking id.
*/

@Entity
@Table(name = "room_numbers_list")
@Getter
@Setter
public class RoomNumbersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id", nullable = false)
    private Integer roomId;

    @Column(name = "room_number", nullable = false)
    private String roomNumber;

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private BookingInfoEntity bookingInfoEntity;
}