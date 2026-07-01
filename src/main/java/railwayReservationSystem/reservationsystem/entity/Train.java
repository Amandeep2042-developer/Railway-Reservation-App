package reservationsystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Train {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long trainNumber;
    private String trainName;
    private String source;
    private String destination;
    private Integer totalSeats;

    @CreationTimestamp
    private LocalDateTime departureTime;

    @CreationTimestamp
    private LocalDateTime arrivalTime;


    private Long availableSeats;

@OneToMany(mappedBy = "train", cascade = CascadeType.ALL, orphanRemoval = true)
private Set<  Booking> booking = new HashSet<>();
}
