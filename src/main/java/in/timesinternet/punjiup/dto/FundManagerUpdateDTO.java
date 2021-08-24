package in.timesinternet.punjiup.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FundManagerUpdateDTO {
    private String  mgrPassword;
    private String firstName;
    private String lastName;
    private String companyName;
    private String educationQualification;
    private String experience;
}
