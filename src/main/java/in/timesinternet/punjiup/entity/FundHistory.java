package in.timesinternet.punjiup.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class FundHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer fundHistId;
    private Double price;
    private Date priceDate;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private FundDetails fundDetails;
    @JsonFormat( pattern = "dd-MM-yyyy hh:mm:ss")
    @CreationTimestamp
    private Date createdAt;
    @JsonFormat( pattern = "dd-MM-yyyy hh:mm:ss")
    @UpdateTimestamp
    private Date updatedAt;
}

