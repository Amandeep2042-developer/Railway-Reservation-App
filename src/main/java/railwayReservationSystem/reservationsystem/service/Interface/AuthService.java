package reservationsystem.service.Interface;


import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.user.OAuth2User;
import reservationsystem.dto.LoginRequestDto;
import reservationsystem.dto.LoginResponseDto;
import reservationsystem.dto.SignUpRequestDto;
import reservationsystem.dto.SignUpResponseDto;

public interface AuthService {


    SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto);

    LoginResponseDto login(LoginRequestDto loginRequestDto);

    ResponseEntity<LoginResponseDto> handleLoginRequest(OAuth2User oAuth2User, String registrationId);
}
