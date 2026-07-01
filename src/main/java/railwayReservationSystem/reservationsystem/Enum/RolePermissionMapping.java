package reservationsystem.Enum;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static reservationsystem.Enum.PermissionType.*;
import static reservationsystem.Enum.RoleType.*;

public class RolePermissionMapping {

    private static final Map<RoleType , Set<PermissionType>> map = Map.of(

            PASSENGER, Set.of(TRAIN_READ ,BOOK_TICKET, CANCEL_TICKET, USER_READ, USER_DELETE),

              ADMIN , Set.of( TRAIN_CREATE, TRAIN_UPDATE, TRAIN_DELETE, TRAIN_READ, USER_READ, USER_DELETE ,BOOK_TICKET, CANCEL_TICKET)

    )  ;


    public static Set<SimpleGrantedAuthority> getAuthoritiesForRole(RoleType role) {
        return map.
                get(role)
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }


}
