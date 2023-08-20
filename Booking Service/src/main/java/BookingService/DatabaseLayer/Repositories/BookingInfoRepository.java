package BookingService.DatabaseLayer.Repositories;
import BookingService.DatabaseLayer.Entities.BookingInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
This interface provides all the methods to perform CRUD operations in the database with table booking.
 */

@Repository
public interface BookingInfoRepository extends JpaRepository<BookingInfoEntity, Integer> {
}