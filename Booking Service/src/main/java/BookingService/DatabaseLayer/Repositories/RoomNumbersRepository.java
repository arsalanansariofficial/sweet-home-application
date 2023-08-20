package BookingService.DatabaseLayer.Repositories;
import BookingService.DatabaseLayer.Entities.RoomNumbersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
This interface provides all the methods to perform CRUD operations in the database with table room_numbers_list.
 */

@Repository
public interface RoomNumbersRepository extends JpaRepository<RoomNumbersEntity, Integer> {
}