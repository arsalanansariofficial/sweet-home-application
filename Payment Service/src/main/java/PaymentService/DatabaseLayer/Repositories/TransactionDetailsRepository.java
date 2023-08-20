package PaymentService.DatabaseLayer.Repositories;
import PaymentService.DatabaseLayer.Entities.TransactionDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
This interface provides all the methods required to perform CRUD operations in the database with the table transaction.
 */

@Repository
public interface TransactionDetailsRepository extends JpaRepository<TransactionDetailsEntity, Integer> {
    TransactionDetailsEntity findTransactionDetailsEntityByBookingId(int bookingId);
}
