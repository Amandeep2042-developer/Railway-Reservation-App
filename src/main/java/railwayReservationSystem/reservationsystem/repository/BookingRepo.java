package reservationsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reservationsystem.entity.Booking;

@Repository
public interface BookingRepo extends JpaRepository<Booking, Long> {

}
