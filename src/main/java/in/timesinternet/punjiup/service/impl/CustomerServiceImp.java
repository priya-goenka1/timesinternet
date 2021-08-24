package in.timesinternet.punjiup.service.impl;

import in.timesinternet.punjiup.dto.*;
import in.timesinternet.punjiup.dto.DeleteCartItemDto;
import in.timesinternet.punjiup.entity.*;
import in.timesinternet.punjiup.entity.enumaration.FundType;
import in.timesinternet.punjiup.entity.enumaration.Role;
import in.timesinternet.punjiup.entity.enumaration.TransactionStatus;
import in.timesinternet.punjiup.exception.NotFoundException;
import in.timesinternet.punjiup.exception.UserAlreadyExistException;
import in.timesinternet.punjiup.repository.CustomerRepository;
import in.timesinternet.punjiup.repository.FundDetailsRepository;
import in.timesinternet.punjiup.repository.FundManagerRepository;
import in.timesinternet.punjiup.repository.TransactionRepository;
import in.timesinternet.punjiup.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImp implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    FundDetailsRepository fundDetailsRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    FundManagerRepository fundManagerRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity login(String email, String password) {
        return null;
    }

    @Override
    public Customer createAccount(CustomerDto customerDto) {

        Customer customer = new Customer();
        customer.setEmail(customerDto.getEmail());
        customer.setAddress(customerDto.getAddress());
        customer.setRole(Role.ROLE_INVESTOR);
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setIsActive(true);
        customer.setPassword(passwordEncoder.encode(customerDto.getPassword()));
        try {
            return customerRepository.save(customer);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new UserAlreadyExistException("Customer with email :" + customer.getEmail() + "already exists");
        }

    }



    @Override
    public List<FundDetails> getAllFunds() {
        List<FundDetails> fundDetailsList =fundDetailsRepository.findAll();
        return fundDetailsList;
    }

    @Override
    public Transaction startTransaction(TransactionDto transactionDto,String userEmail) {
        Optional<Transaction> transactionOptional=transactionRepository.findByTransactionId(transactionDto.getFundId());
        if(transactionOptional.isPresent()) {
            Transaction transaction = transactionOptional.get();
            transaction.setTransactionType(transactionDto.getTransactionType());
            transaction.setAmount(transactionDto.getAmount());
            Optional<Customer>customerOptional=customerRepository.findByEmail(userEmail);
           if(customerOptional.isPresent()){
               Customer customer=customerOptional.get();
            transaction.setCustomer(customer);
            FundDetails fundDetails = fundDetailsRepository.getById(transactionDto.getFundId());
            transaction.setNav(fundDetails.getNav());
            Double shares = transactionDto.getAmount() / fundDetails.getNav();
            transaction.setTotalShares(shares);
            transaction.setTransactionStatus(TransactionStatus.inCart);
            transaction.setFundDetails(fundDetails);
            return transactionRepository.save(transaction);
        }
        else
            throw  new NotFoundException("Customer  is not found with given email id :"+userEmail);
        }

        else{
            throw new NotFoundException("Transaction is not found with given id :"+transactionDto.getFundId());
        }

    }


    @Override
    public Customer updateCustomer(InvestorUpdateDto investorUpdateDto,String userEmail) {
        Optional<Customer> customerOptional = customerRepository.findByEmail(userEmail);
        if (customerOptional.isPresent()) {

            Customer customer = customerOptional.get();
            customer.setAddress(investorUpdateDto.getAddress());
            customer.setFirstName(investorUpdateDto.getFirstName());
            customer.setLastName(investorUpdateDto.getLastName());
            return customerRepository.save(customer);
        } else {
            throw new NotFoundException("Customer is not found with given email :"+userEmail);
        }
    }
    @Override
    public FundDetails getFund(int FundId) {
        return fundDetailsRepository.getById(FundId);
    }

    @Override
    public List<FundDetails> getAllTypeFund(FundType fundtype) {
        return fundDetailsRepository.findAllByFundType(fundtype);
    }

    @Override
    public List<Transaction> showCart(int customerId){
        Customer customer=customerRepository.getById(customerId);
        System.out.print(customer);
        return transactionRepository.findAllByTransactionStatusAndCustomer(TransactionStatus.inCart,customer);
    }

    @Override
    public String deleteTransaction(DeleteCartItemDto deleteCartItemDto){
        transactionRepository.deleteById(deleteCartItemDto.getTransactionId());
        return "Successfully Deleted";
    }

    @Override
    public List<FundManager> getFundManagerBySearch(String search1, String search2) {
        return fundManagerRepository.findFundManagerByFirstNameContainingOrLastNameContaining(search1,search2);
    }

    @Override
    public List<FundDetails> getFundsBySearch(String search){
        return fundDetailsRepository.findAllByFundNameContainingIgnoreCase(search);
    }



    public List<CustomerFund> customerPositionDetail( CustomerFund customerFund){
        return null;
    }

    public List<Transaction> buyCart(BuyDto buyDto ){

             Customer customer=customerRepository.getById(buyDto.getCustomerId());

            List<Transaction> transactionList = transactionRepository.findAllByTransactionStatusAndCustomer(TransactionStatus.inCart, customer);
            for (Transaction t : transactionList) {
                t.setTransactionStatus(TransactionStatus.Pending);
                Transaction transaction = transactionRepository.save(t);
            }
            return transactionList;
        }


   @Override
    public  Transaction buy(TransactionDto transactionDto,String userEmail){
       Optional<Transaction> transactionOptional=transactionRepository.findByTransactionId(transactionDto.getFundId());
       if(transactionOptional.isPresent()) {
           Transaction transaction = transactionOptional.get();
           transaction.setTransactionType(transactionDto.getTransactionType());
           transaction.setAmount(transactionDto.getAmount());
           Optional<Customer>customerOptional=customerRepository.findByEmail(userEmail);
           if(customerOptional.isPresent()){
               Customer customer=customerOptional.get();
               transaction.setCustomer(customer);
               FundDetails fundDetails = fundDetailsRepository.getById(transactionDto.getFundId());
               transaction.setNav(fundDetails.getNav());
               Double shares = transactionDto.getAmount() / fundDetails.getNav();
               transaction.setTotalShares(shares);
               transaction.setTransactionStatus(TransactionStatus.inCart);
               transaction.setFundDetails(fundDetails);
               return transactionRepository.save(transaction);
           }
           else
               throw  new NotFoundException("Customer  is not found with given email id :"+userEmail);
       }

       else{
           throw new NotFoundException("Transaction is not found with given id :"+transactionDto.getFundId());
       }

   }




}
