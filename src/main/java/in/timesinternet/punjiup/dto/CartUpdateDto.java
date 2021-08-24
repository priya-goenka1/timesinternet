package in.timesinternet.punjiup.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartUpdateDto {
    int transactionId;
    Double amount;
    Integer fundId;
    Integer customerId;
}

