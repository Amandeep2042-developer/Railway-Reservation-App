package reservationsystem.dto;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
public class TrainResponseDto {

    private Long id;

    private Long trainNumber;

    private String trainName;

    private String source ;

    private String destination;

    @CreationTimestamp
    private LocalDateTime departureTime;

    @CreationTimestamp
    private LocalDateTime arrivalTime;

    private Long availableSeats;




}
