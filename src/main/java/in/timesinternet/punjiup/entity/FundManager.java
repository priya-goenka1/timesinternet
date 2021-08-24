package in.timesinternet.punjiup.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class FundManager extends User{

    @Column(nullable = false, length = 50)
    private String companyName;
    @Column(nullable = false)
    private String educationQualification;
    @Column(nullable = false)
    private String experience;
    @JsonIgnore
    @OneToMany(mappedBy = "fundManager", cascade = CascadeType.PERSIST)
    List<FundDetails> fundDetailsList = new ArrayList<FundDetails>();
    @JsonFormat( pattern = "dd-MM-yyyy hh:mm:ss")
    @CreationTimestamp
    private Date createdAt;
    @JsonFormat( pattern = "dd-MM-yyyy hh:mm:ss")
    @UpdateTimestamp
    private Date updatedAt;
    void addFund(FundDetails fundDetails)
    {
        fundDetailsList.add(fundDetails);
        fundDetails.setFundManager(this);
    }

}
