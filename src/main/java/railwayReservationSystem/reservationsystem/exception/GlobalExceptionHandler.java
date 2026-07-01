package reservationsystem.exception;

import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiError>  usernameNotFoundException( UsernameNotFoundException exception){
        ApiError apiError = new ApiError("Username not found  with username : "+exception.getMessage() , HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
      //  return new ResponseEntity<>(apiError, apiError.getStatusCode());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError>  authenticationException( AuthenticationException ex){
        ApiError apiError = new ApiError("Authentication failed : "+ex.getMessage() , HttpStatus.UNAUTHORIZED);
        return  new ResponseEntity<>(apiError , apiError.getStatusCode());
    }

    @ExceptionHandler (JwtException.class)
     public  ResponseEntity<ApiError> jwtException(JwtException jwtException){
        ApiError apiError = new ApiError("Invalid jwt token :"+jwtException.getMessage(), HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(apiError , apiError.getStatusCode());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> accessDeniedException(AccessDeniedException accessDeniedException){
        ApiError apiError = new ApiError("Access denied : Insufficient permission "+accessDeniedException.getMessage() , HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(apiError , apiError.getStatusCode());

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> exceptions(Exception e ){
        ApiError apiError = new ApiError("An unexpected error occurred : "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(apiError ,apiError.getStatusCode());
    }

}
