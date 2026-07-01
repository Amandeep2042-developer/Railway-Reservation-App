package reservationsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reservationsystem.entity.Train;

@Repository
public interface TrainRepo extends JpaRepository<Train, Long> {
}
