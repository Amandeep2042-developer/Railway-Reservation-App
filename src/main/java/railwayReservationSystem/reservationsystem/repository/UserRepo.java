package reservationsystem.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import reservationsystem.entity.AppUser;
import reservationsystem.Enum.ProviderType;


import java.util.Optional;

public interface UserRepo extends JpaRepository<AppUser, Long >{


    Optional<AppUser> findByUsername(String username);

   Optional<AppUser> findByProviderIdAndProviderType(String providerId, ProviderType providerType);
}
