package in.timesinternet.punjiup.service;

import in.timesinternet.punjiup.dto.*;
import in.timesinternet.punjiup.entity.FundDetails;
import in.timesinternet.punjiup.entity.FundManager;
import in.timesinternet.punjiup.entity.Transaction;
import in.timesinternet.punjiup.entity.enumaration.FundType;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface FundManagerService {
    FundManager createFundManagerAccount(FundmanagerDto fundmanagerDto);
    ResponseEntity login(String email, String password);
    FundManager UpdateManager(FundManagerUpdateDTO fundManagerUpdateDTO,String userEmail);
    FundDetails addFund(FundDto fundDto,String userEmail);
    FundDetails fundUpdate(FundUpdateDto fundUpdateDto);
    List<FundDetails> getAllFund(String userEmail);
    FundDetails getFund( int fundId,String userEmail);
    List<FundDetails> getAllTypeFund(FundType fundType,String email);
    Transaction updateTransactionStatus(TransactionStatusUpdateDto transactionStatusUpdateDto);
    List<FundManager>getAllFundManager();
    List<Transaction> getAllTransaction(String userEmail);
    FundManager getFundManager(String email);







}
