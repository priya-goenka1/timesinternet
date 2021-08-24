package in.timesinternet.punjiup.entity.embeddable;

import in.timesinternet.punjiup.entity.FundDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CloseEndFund {
    Date bStartDate;
    Date bEndDate;
    Integer lockInPeriod;

}
