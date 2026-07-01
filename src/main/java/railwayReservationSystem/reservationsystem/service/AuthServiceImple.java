package reservationsystem.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import reservationsystem.dto.LoginRequestDto;
import reservationsystem.dto.LoginResponseDto;
import reservationsystem.dto.SignUpRequestDto;
import reservationsystem.dto.SignUpResponseDto;
import reservationsystem.entity.Passenger;
import reservationsystem.entity.AppUser;
import reservationsystem.Enum.ProviderType;
import reservationsystem.Enum.RoleType;
import reservationsystem.repository.PassengerRepo;
import reservationsystem.repository.UserRepo;
import reservationsystem.service.Interface.AuthService;
import reservationsystem.utils.AuthUtil;

import java.util.Set;


@Service
@RequiredArgsConstructor
public class AuthServiceImple implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
private  final PassengerRepo passengerRepo;

    @Transactional
    public AppUser signUpInternal
            (SignUpRequestDto signUpRequestDto, ProviderType providerType, String providerId) {
        AppUser user = userRepo.
                findByUsername(signUpRequestDto.getUsername()).orElse(null);
        if (user != null) {
            throw new IllegalArgumentException("User already exists");
        }
        user = AppUser.builder()
                .username(signUpRequestDto.getUsername())
                .providerType(providerType)
                .providerId(providerId)
                .role(Set.of(RoleType.PASSENGER))
                .build();

        if (providerType == ProviderType.EMAIL) {
            user.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
        }
        userRepo.save(user);

        Passenger passenger = Passenger.builder()
                .name(signUpRequestDto.getName())
                .username(signUpRequestDto.getUsername())
                .users(user)
                .build();

        passengerRepo.save(passenger);

        return user;

    }

    @Transactional
    @Override
    public SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto) {
        AppUser users = signUpInternal(signUpRequestDto, ProviderType.EMAIL, null);

        return modelMapper.map(users, SignUpResponseDto.class);

    }

    @Transactional
    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.
                authenticate
                        (new UsernamePasswordAuthenticationToken
                                (loginRequestDto.getUsername(), loginRequestDto.getPassword()));

        AppUser users = (AppUser) authentication.getPrincipal();

        String token = authUtil.generateAccessToken(users);

        return new LoginResponseDto(users.getId(), token);

    }

    @Transactional
    @Override
    public ResponseEntity<LoginResponseDto> handleLoginRequest
            (OAuth2User oAuth2User, String registrationId) {


        // providerType
        ProviderType providerType =
                authUtil.getProviderTypeFromRegistrationId(registrationId);

        // providerId
        String providerId =
                authUtil.getProviderIdFromOAuth2User(oAuth2User, registrationId);

        AppUser users =
                userRepo.findByProviderIdAndProviderType(providerId, providerType).
                        orElse(null);

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        AppUser emailUser =
                userRepo.findByUsername(email).orElse(null);


        if (emailUser == null && users == null) {

            String username =
                    authUtil.determineUserFromOAuth2User(oAuth2User, registrationId, providerId);

            users = signUpInternal

                    (new SignUpRequestDto
                            (name, username, null, Set.of(RoleType.PASSENGER)), providerType, providerId);


        } else if (users != null) {
            if (email != null && !email.isBlank() && !email.equals(emailUser)) {
                users.setUsername(email);
                userRepo.save(users);
            }

        } else {
            throw new BadCredentialsException
                    ("This email is already registered with provider " + emailUser.getProviderType());
        }
        LoginResponseDto loginResponseDto = new LoginResponseDto
                (users.getId(), authUtil.generateAccessToken(users));
        return ResponseEntity.ok(loginResponseDto);


    }

}