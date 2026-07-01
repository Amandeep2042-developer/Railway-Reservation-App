package reservationsystem.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import reservationsystem.Enum.ProviderType;
import reservationsystem.Enum.RolePermissionMapping;
import reservationsystem.Enum.RoleType;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AppUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
 private ProviderType providerType;

 private  String providerId;
    private String username;
 private String password;

 @ElementCollection(fetch = FetchType.EAGER)
 @Enumerated(EnumType.STRING)
    private Set<RoleType> role = new HashSet<>() ;

 @OneToOne(mappedBy =  "users", cascade = CascadeType.ALL)
 private  Passenger passenger;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//
       Set<SimpleGrantedAuthority> authorities = new HashSet<>();
      role.forEach(
              role ->{
                  Set<SimpleGrantedAuthority> permission = RolePermissionMapping.getAuthoritiesForRole(role);
                      authorities.addAll(permission);
                      authorities.add(new SimpleGrantedAuthority("ROLE_"+role.name()));
                  }


      );
      return authorities;
    }
}
