package in.timesinternet.punjiup.dto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
@Getter
@Setter
public class LoginDto {
    @Email(message = "invalid email")
    @NotNull(message = "email can't be null")
    String email;
    @NotNull(message = "password can't be null")
    String password;
}
