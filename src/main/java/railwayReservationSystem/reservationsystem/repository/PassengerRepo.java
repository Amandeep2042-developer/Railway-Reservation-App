package reservationsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reservationsystem.entity.Passenger;

@Repository
public interface PassengerRepo extends JpaRepository<Passenger, Long> {


}
