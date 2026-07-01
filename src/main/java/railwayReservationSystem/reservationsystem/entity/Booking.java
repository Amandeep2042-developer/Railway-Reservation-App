package reservationsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long Id;

    @ElementCollection
    private List<Integer> seatNumber;

    private Long numberOfSeats;
    private String bookingStatus;

    private Long totalFare;

   private LocalDateTime bookingDateTime;

   @ManyToOne
   @JoinColumn
   private Passenger appUser;

   @ManyToOne()
   @JoinColumn
   private Train train;


}
