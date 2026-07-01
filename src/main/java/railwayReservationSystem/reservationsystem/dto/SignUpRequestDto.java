package reservationsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import reservationsystem.Enum.RoleType;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDto {

private  String name;
    private String username;
    private String password;
  Set<RoleType> role = new HashSet<>();

}
