package in.timesinternet.punjiup.dto;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.persistence.Column;
@Getter
@Setter
public class FundmanagerDto {
    @Email(message = "invalid email")
    @NotNull(message = "email can't be null")
    private String email;
    @NotNull(message = "password can't be null")
    private String password;
    @NotNull(message = "firstName can't be null")
    private String firstName;
    @NotNull(message = "lastName can't be null")
    private String lastName;
    @NotNull(message = "company name can't be null")
    private String companyName;
    @NotNull(message = "educationQualification can't be null")
    private String educationQualification;
    @NotNull(message = "experience can't be null")
    private String experience;
}
