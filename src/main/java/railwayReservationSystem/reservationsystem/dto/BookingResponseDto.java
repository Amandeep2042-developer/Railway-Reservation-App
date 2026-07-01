package reservationsystem.dto;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BookingResponseDto {

    private Long bookingId;


    private Long numberOfSeats;
    private List<Integer> seatNumber;
    private Long totalFare;
    private String bookingStatus;
    @CreationTimestamp
    private LocalDateTime bookingDateTime;

}
