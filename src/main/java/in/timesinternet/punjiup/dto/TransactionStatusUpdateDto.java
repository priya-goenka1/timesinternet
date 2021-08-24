package in.timesinternet.punjiup.dto;

import in.timesinternet.punjiup.entity.enumaration.TransactionStatus;
import in.timesinternet.punjiup.entity.enumaration.TransactionType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionStatusUpdateDto {
    Integer transactionId;
    Double amount;
    Integer fundId;
    Integer customerId;
}
