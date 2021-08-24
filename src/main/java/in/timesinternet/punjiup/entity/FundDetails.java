package in.timesinternet.punjiup.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import in.timesinternet.punjiup.entity.embeddable.CloseEndFund;
import in.timesinternet.punjiup.entity.enumaration.FundType;
import in.timesinternet.punjiup.entity.enumaration.IsActive;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@ToString
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class FundDetails {
    @Id
    @GeneratedValue
    private Integer fundId;
    @Column(nullable = false, unique = true,updatable = false)
    private String fundName;
    @Column(nullable = false, unique = true,updatable = false)
    private String symbol;
    private Double totalValue;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FundType fundType;
    @Embedded
    private CloseEndFund closeEndFund;
    private Double nav;
    @Column(nullable = false)
    private Double expenseRatio;
    @Column(nullable = false)
    private Integer preference;
    @Enumerated(EnumType.STRING)
    private IsActive isActive;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private FundManager fundManager;
    @JsonIgnore
    @OneToMany(mappedBy = "fundDetails", cascade = CascadeType.PERSIST)
    private List<Transaction> transactionList = new ArrayList<Transaction>();
    @JsonIgnore
    @OneToMany(mappedBy = "fundDetails", cascade = CascadeType.ALL)
    private List<FundHistory> fundHistories = new ArrayList<FundHistory>();
    @JsonFormat( pattern = "dd-MM-yyyy hh:mm:ss")
    @CreationTimestamp
    private Date createdAt;
    @JsonFormat( pattern = "dd-MM-yyyy hh:mm:ss")
    @UpdateTimestamp
    private Date updatedAt;
    @Column(nullable = false)
    private Double exitLoad;
    void addTransaction(Transaction transaction)
    {
        transactionList.add(transaction);
        transaction.setFundDetails(this);
    }
    void addFundHistory(FundHistory fundHistory)
    {
        fundHistories.add(fundHistory);
        fundHistory.setFundDetails(this);
    }
}
