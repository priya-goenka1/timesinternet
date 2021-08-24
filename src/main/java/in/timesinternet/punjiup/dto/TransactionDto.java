package in.timesinternet.punjiup.dto;
import in.timesinternet.punjiup.entity.enumaration.TransactionStatus;
import in.timesinternet.punjiup.entity.enumaration.TransactionType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@ToString
public class TransactionDto {
    TransactionType transactionType;
    @NotNull(message = "amount can't be null")
    Double amount;
    Integer fundId;
}
