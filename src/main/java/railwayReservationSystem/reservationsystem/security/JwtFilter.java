package reservationsystem.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import reservationsystem.entity.AppUser;
import reservationsystem.repository.UserRepo;
import reservationsystem.utils.AuthUtil;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor

public class JwtFilter extends OncePerRequestFilter {
    private final AuthUtil authUtil;
    private  final UserRepo userRepo;
    private  final HandlerExceptionResolver handlerExceptionResolver;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            log.info("incoming request : {}", request.getRequestURI());

            final String headerToken = request.getHeader("Authorization");
            if (headerToken == null || !headerToken.startsWith("Bearer")) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = headerToken.split("Bearer ")[1];
            String username = authUtil.getUsernameFromToken(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                AppUser appUser = userRepo.findByUsername(username).orElseThrow();
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken
                                (appUser, null, appUser.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            }
            filterChain.doFilter(request, response);
        }catch (Exception  ex){
            handlerExceptionResolver.resolveException(request , response , null ,ex);

        }



    }
}
