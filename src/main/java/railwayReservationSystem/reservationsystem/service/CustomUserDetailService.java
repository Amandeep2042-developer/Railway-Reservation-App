package reservationsystem.service;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reservationsystem.repository.UserRepo;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username).orElseThrow( () -> new IllegalArgumentException("User not found"));
    }
}
