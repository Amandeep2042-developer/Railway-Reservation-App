package reservationsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reservationsystem.dto.LoginRequestDto;
import reservationsystem.dto.LoginResponseDto;
import reservationsystem.dto.SignUpRequestDto;
import reservationsystem.dto.SignUpResponseDto;
import reservationsystem.service.Interface.AuthService;

@RestController
@RequestMapping("/auth/user")
@RequiredArgsConstructor
public class AuthController {

private final AuthService authService;

    @PostMapping("/signUp")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto){
        return ResponseEntity.ok(authService.signUp(signUpRequestDto));
    }

    @PostMapping("/login")
public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }
}
