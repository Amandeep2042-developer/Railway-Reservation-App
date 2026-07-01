package reservationsystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PassengerRequestDto {

    @NotBlank
private  String name;

    private Long age;
    private  String gender;
    @Email
private String username;

    private String password;

    @NotBlank
private String role;

}
