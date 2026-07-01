package reservationsystem.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class TrainRequestDto {

     private Long trainNumber;

     @Column(nullable = false)
     private String trainName;

      @Column(nullable = false)
    private String source ;

      @Column(nullable = false)
    private String destination;


    private Integer totalSeats;

    private Long availableSeats;



}
