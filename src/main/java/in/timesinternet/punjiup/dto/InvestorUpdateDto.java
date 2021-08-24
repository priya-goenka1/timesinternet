package in.timesinternet.punjiup.dto;

import in.timesinternet.punjiup.entity.embeddable.Address;
import in.timesinternet.punjiup.entity.enumaration.IsActive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvestorUpdateDto {
    private String firstName;
    private String lastName;
    private IsActive isActive;
    Address address;
}
