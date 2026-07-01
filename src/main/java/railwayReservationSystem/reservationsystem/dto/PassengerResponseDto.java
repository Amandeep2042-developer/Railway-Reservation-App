package reservationsystem.dto;

import lombok.Data;

@Data
public class PassengerResponseDto {
    private Long id;
    private  String name;
    private Long age;
    private String email;
    private String role;
}
