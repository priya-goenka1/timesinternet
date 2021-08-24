package in.timesinternet.punjiup.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import in.timesinternet.punjiup.entity.embeddable.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer extends User {
    @Embedded
    Address address;
    private Date lastTradeDate;
    @JsonIgnore
    @OneToMany(mappedBy = "customer", cascade = CascadeType.PERSIST)
    private List<Transaction> transactionList = new ArrayList<Transaction>();
    @JsonIgnore
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<CustomerFund> customerFundList = new ArrayList<CustomerFund>();
    @JsonFormat( pattern = "dd-MM-yyyy hh:mm:ss")
    @CreationTimestamp
    private Date createdAt;
    @JsonFormat( pattern = "dd-MM-yyyy hh:mm:ss")
    @UpdateTimestamp
    private Date updatedAt;
    void addTransaction(Transaction transaction)
    {
        transactionList.add(transaction);
        transaction.setCustomer(this);
    }
    void addCustomerFund(CustomerFund customerFund)
    {
        customerFundList.add(customerFund);
        customerFund.setCustomer(this);
    }


}
