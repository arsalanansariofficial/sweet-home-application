package PaymentService.DatabaseLayer.Entities;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

/*
This class corresponds to transaction table in the database.
 */

@Entity
@Table(name = "transaction")
@Getter
@Setter
public class TransactionDetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private int id;

    @Column(name = "payment_mode")
    private String paymentMode;

    @Column(name = "booking_id", nullable = false)
    private int bookingId;

    @Column(name = "upi_id")
    private String upiId;

    @Column(name = "card_number")
    private String cardNumber;
}
