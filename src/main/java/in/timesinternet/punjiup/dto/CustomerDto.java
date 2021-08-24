package in.timesinternet.punjiup.dto;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import in.timesinternet.punjiup.entity.embeddable.Address;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDto {
    @Email(message = "invalid email")
    @NotNull(message = "email can't be null")
    private String email;
    @NotNull(message = "Password can't be null")
    private String password;
    @NotNull(message = "firstName can't be null")
    private String firstName;
    @NotNull(message = "lastName can't be null")
    private String lastName;
    Address address;
}
