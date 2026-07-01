package reservationsystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignUpResponseDto {
    private Long id;
    private String name ;
    private String username;
    private String role;


}
