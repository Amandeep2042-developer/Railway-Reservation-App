package reservationsystem.dto;


import lombok.Data;
import reservationsystem.Enum.RoleType;

import java.util.ArrayList;
import java.util.List;

@Data
public class AssignNewAdmin {

    private String name ;
    private String username;
    private String password;
     List<RoleType> roles = new ArrayList<>();


}
