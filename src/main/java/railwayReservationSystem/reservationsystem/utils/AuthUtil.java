package reservationsystem.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import reservationsystem.entity.AppUser;
import reservationsystem.Enum.ProviderType;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
public class AuthUtil {

    @Value("${railway_secret_key}")
    private String jwtSecretKey;

    private SecretKey getSecretKey(){

return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));


    }

public String generateAccessToken(AppUser users){
        return Jwts.builder()
                .subject(users.getUsername())
                .claim("userId" , users.getId().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000*60*10))
                .signWith(getSecretKey())
                .compact();
}

public String getUsernameFromToken(String token ){
        Claims claims =  Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();

}

    public ProviderType getProviderTypeFromRegistrationId(String registrationId) {
        return  switch(registrationId.toLowerCase()){
            case "google" -> ProviderType.GOOGLE;
            case "github" -> ProviderType.GITHUB;
            case "facebook"-> ProviderType.FACEBOOK;
            default ->  throw  new IllegalArgumentException("Unsupported providerType with registrationId "+registrationId);
        };
    }

    public String getProviderIdFromOAuth2User(OAuth2User oAuth2User, String registrationId) {
        String providerId = switch (registrationId.toLowerCase())
        {
            case "google" -> oAuth2User.getAttribute("sub");

            case "github" -> {
               Object id = oAuth2User.getAttribute("id");
               yield id != null ? id.toString() : null ;

            }

            default -> {
                log.error("Unsupported OAuth2 provider {} ", registrationId);
                throw new IllegalArgumentException("Unsupported OAuth2 provider" +registrationId);
            }
            };

        if(providerId == null || providerId.isBlank()){
            log.error("Unable to determine providerId  {} ", registrationId);
            throw new IllegalArgumentException("Unable to determine providerId for OAuth2Login " +registrationId);
        }


        return providerId;

    }

    public String determineUserFromOAuth2User(OAuth2User oAuth2User, String registrationId, String providerId) {

        String email = oAuth2User.getAttribute("email");
        if(email !=  null && !email.isBlank()){
            return email;
        }

      return  switch (registrationId.toLowerCase()) {
          case "google" -> oAuth2User.getAttribute("sub");
          case "github" -> oAuth2User.getAttribute("login");
          default -> providerId;
      };


    }
}
