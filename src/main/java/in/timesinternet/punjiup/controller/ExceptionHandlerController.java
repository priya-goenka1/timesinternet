package in.timesinternet.punjiup.controller;
import in.timesinternet.punjiup.exception.InvalidRequestBodyException;
import in.timesinternet.punjiup.exception.NotFoundException;
import in.timesinternet.punjiup.exception.UnauthorizedException;
import in.timesinternet.punjiup.exception.UserAlreadyExistException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.HashMap;
@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(value = {InvalidRequestBodyException.class, UserAlreadyExistException.class,
            NotFoundException.class, UnauthorizedException.class})
    public ResponseEntity<HashMap<String, String>> runTimeExceptionHandler(RuntimeException runtimeException) {

        return ResponseEntity.badRequest().body(new HashMap<String, String>() {{
            put("message", runtimeException.getMessage());
        }});
    }
}