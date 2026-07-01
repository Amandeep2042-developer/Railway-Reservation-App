package reservationsystem.dto;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class BookingRequestDto {

    @Column(nullable = false)
    private Long trainId;

    @Column(nullable = false)
    private Long userId;
    private List<Integer> seatNumber;
     private Long numberOfSeats;
    private Long totalFare;

    @CreationTimestamp
    private LocalDateTime bookingDateTime;

}
