package reservationsystem.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;


import java.io.IOException;

import static reservationsystem.Enum.PermissionType.*;
import static reservationsystem.Enum.RoleType.*;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class WebSecurityConfig {
private  final  JwtFilter jwtFilter;

private final OAuth2SuccessHandler oAuth2SuccessHandler;
private final HandlerExceptionResolver handlerExceptionResolver;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.
                csrf(csrfConfigurer -> csrfConfigurer.disable())
                .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests( auth -> auth.requestMatchers("/auth/user/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE ,"/admin/**")
                        .hasAnyAuthority(TRAIN_DELETE.name() , USER_READ.name())
                        .requestMatchers("/passenger/**").hasAnyRole(PASSENGER.name(), ADMIN.name())
                        .anyRequest().authenticated())
                .addFilterBefore(jwtFilter , UsernamePasswordAuthenticationFilter.class)
                .oauth2Login( oAuth2 -> oAuth2.failureHandler((request, response, exception) -> {
                    log.error("oAuth2 error {} " , exception.getMessage());
                                    handlerExceptionResolver.resolveException(request, response ,null ,exception);

                                })
                                .successHandler(oAuth2SuccessHandler)

                )
                .exceptionHandling(exceptionHandlingConfigurer -> exceptionHandlingConfigurer.accessDeniedHandler(
                        new AccessDeniedHandler() {
                            @Override
                            public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
                                handlerExceptionResolver.resolveException(request, response ,null ,accessDeniedException);
                            }
                        }


                ));
        ;

        return httpSecurity.build();

    }


}
