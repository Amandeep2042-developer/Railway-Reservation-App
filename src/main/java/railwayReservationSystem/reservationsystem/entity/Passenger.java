package reservationsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private Long age;
    private  String gender;


    private String username;

    private String password;


    @OneToOne
    @MapsId
    private AppUser users;

    @OneToMany(mappedBy = "appUser" , cascade = CascadeType.ALL, orphanRemoval = true)
 private List<Booking> booking;

}
