package in.timesinternet.punjiup.repository;
import in.timesinternet.punjiup.entity.Customer;
import in.timesinternet.punjiup.entity.FundManager;
import in.timesinternet.punjiup.entity.Transaction;
import in.timesinternet.punjiup.entity.enumaration.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findTransactionByTransactionStatusAndFundDetailsFundManager(TransactionStatus transactionStatus, FundManager fundManager);
    List<Transaction>findAllByFundDetailsFundManagerAndTransactionStatus(FundManager fundManager, TransactionStatus transactionStatus);
    List<Transaction>findAllByCustomerAndTransactionStatus(Customer customer, TransactionStatus transactionStatus);
    Optional<Transaction> findByTransactionId(Integer id);
    List<Transaction>findAllByTransactionStatusAndCustomer(TransactionStatus transactionStatus, Customer customer);
}
