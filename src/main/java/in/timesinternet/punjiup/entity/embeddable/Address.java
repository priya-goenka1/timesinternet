package in.timesinternet.punjiup.entity.embeddable;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Address {
    String line1;
    String line2;
    String pinCode;
    String city;
    String state;

}
