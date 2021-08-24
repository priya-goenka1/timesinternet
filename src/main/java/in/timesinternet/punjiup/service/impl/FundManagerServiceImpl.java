package in.timesinternet.punjiup.service.impl;
import in.timesinternet.punjiup.dto.*;
import in.timesinternet.punjiup.entity.Customer;
import in.timesinternet.punjiup.entity.FundDetails;
import in.timesinternet.punjiup.entity.FundManager;
import in.timesinternet.punjiup.entity.Transaction;
import in.timesinternet.punjiup.entity.enumaration.*;
import in.timesinternet.punjiup.exception.NotFoundException;
import in.timesinternet.punjiup.exception.UserAlreadyExistException;
import in.timesinternet.punjiup.repository.CustomerRepository;
import in.timesinternet.punjiup.repository.FundDetailsRepository;
import in.timesinternet.punjiup.repository.FundManagerRepository;
import in.timesinternet.punjiup.repository.TransactionRepository;
import in.timesinternet.punjiup.service.FundManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class FundManagerServiceImpl implements FundManagerService {
    @Autowired
    FundManagerRepository fundManagerRepository;
    @Autowired
    FundDetailsRepository fundDetailsRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public FundManager createFundManagerAccount(FundmanagerDto fundmanagerDto) {
        FundManager fundManager=new FundManager();
        fundManager.setEmail(fundmanagerDto.getEmail());
        fundManager.setCompanyName(fundmanagerDto.getCompanyName());
        fundManager.setEducationQualification(fundmanagerDto.getEducationQualification());
        fundManager.setExperience(fundmanagerDto.getExperience());
        fundManager.setFirstName(fundmanagerDto.getFirstName());
        fundManager.setLastName(fundmanagerDto.getLastName());
        fundManager.setRole(Role.ROLE_FUNDMANAGAER);
        fundManager.setIsActive(true);
        fundManager.setPassword(passwordEncoder.encode(fundmanagerDto.getPassword()));
        try
        {
            return fundManagerRepository.save(fundManager);
        }
        catch (DataIntegrityViolationException dataIntegrityViolationException)
        {
            throw new UserAlreadyExistException("FundManager with email :- "+fundManager.getEmail()+" already exists");
        }
    }

    @Override
    public FundManager UpdateManager(FundManagerUpdateDTO fundManagerUpdateDTO,String userEmail) {
        Optional<FundManager >fundManagerOptional=fundManagerRepository.findByEmail(userEmail);
        if(fundManagerOptional.isPresent())
        {
            FundManager fundManager=fundManagerOptional.get();
            fundManager.setCompanyName(fundManagerUpdateDTO.getCompanyName());
            fundManager.setEducationQualification(fundManagerUpdateDTO.getEducationQualification());
            fundManager.setExperience(fundManagerUpdateDTO.getExperience());
            fundManager.setFirstName(fundManagerUpdateDTO.getFirstName());
            fundManager.setLastName(fundManagerUpdateDTO.getLastName());
            fundManager.setPassword(fundManagerUpdateDTO.getMgrPassword());
            return fundManagerRepository.save(fundManager);
        }
        else
        {
            throw new NotFoundException("Manager is not found with given Id :- " + userEmail);

        }


    }

    @Override
    public FundDetails addFund(FundDto fundDto,String userEmail) {
        FundDetails fundDetails=new FundDetails();
        fundDetails.setFundName(fundDto.getFundName());
        fundDetails.setFundType(fundDto.getFundType());
        fundDetails.setExitLoad(fundDto.getExitLoad());
        fundDetails.setNav(fundDto.getNav());
        fundDetails.setExitLoad(fundDto.getExitLoad());
        fundDetails.setExpenseRatio(fundDto.getExpenseRatio());
        fundDetails.setPreference(fundDto.getPreference());
        fundDetails.setSymbol(fundDto.getSymbol());
        fundDetails.setCloseEndFund(fundDto.getCloseEndFund());
        Optional<FundManager> fundManagero=fundManagerRepository.findByEmail(userEmail);
        if(fundManagero.isPresent())
        {
            FundManager fundManager=fundManagero.get();
            fundDetails.setFundManager(fundManager);
            fundDetails.setTotalValue(fundDto.getTotalValue());
            IsActive isActive=IsActive.Yes;
            fundDetails.setIsActive(isActive);
        }
        else
            {
                throw new NotFoundException("Manager is not found with given Email :- " + userEmail);

            }
        try {
            fundDetails = fundDetailsRepository.save(fundDetails);
            return fundDetails;
        } catch (
                DataIntegrityViolationException dataIntegrityViolationException) {
            throw new UserAlreadyExistException("Fund with given details :- " + fundDetails.getFundName() + " already exits");
        }

    }

    @Override
    public FundDetails fundUpdate(FundUpdateDto fundUpdateDto) {
        Optional< FundDetails >optionalFundDetails=fundDetailsRepository.findById(fundUpdateDto.getFundId());
        if(optionalFundDetails.isPresent())
        {
            FundDetails fundDetails=optionalFundDetails.get();
            fundDetails.setFundType(fundUpdateDto.getFundType());
            fundDetails.setExitLoad(fundUpdateDto.getExitLoad());
            fundDetails.setNav(fundUpdateDto.getNav());
            fundDetails.setExitLoad(fundUpdateDto.getExitLoad());
            fundDetails.setExpenseRatio(fundUpdateDto.getExpenseRatio());
            fundDetails.setPreference(fundUpdateDto.getPreference());
            fundDetails.setCloseEndFund(fundUpdateDto.getCloseEndFund());
            fundDetails.setIsActive(fundUpdateDto.getIsActive());
            fundDetails.setTotalValue(fundUpdateDto.getTotalValue());
            return fundDetailsRepository.save(fundDetails);
        }
        else
        {
            throw new NotFoundException("Fund is not found with given Id :- " + fundUpdateDto.getFundId());

        }
    }

    @Override
    public List<FundDetails> getAllFund(String userEmail) {
        Optional<FundManager> fundManagerOptional=fundManagerRepository.findByEmail(userEmail);
        if(fundManagerOptional.isPresent())
        {
            FundManager fundManager=fundManagerOptional.get();
            List<FundDetails> fundDetailsList=fundDetailsRepository.findAllByFundManagerOrderByPreferenceAsc(fundManager);
            return fundDetailsList;
        }
        else
        {
            throw new NotFoundException("Manager is not found with given Email :- " + userEmail);

        }
    }

    @Override
    public FundDetails getFund(int fundId,String email) {
        FundManager fundManager=fundManagerRepository.findByEmail(email).get();
       FundDetails fundDetails=fundDetailsRepository.findByFundIdAndFundManager(fundId,fundManager);
       return fundDetails;
    }

    @Override
    public List<FundDetails> getAllTypeFund(FundType fundType,String email) {
        FundManager fundManager=fundManagerRepository.findByEmail(email).get();
        List<FundDetails> fundDetailsList =fundDetailsRepository.findAllByFundTypeAndFundManagerOrderByPreferenceAsc(fundType,fundManager);
        return fundDetailsList;
    }

    @Override
    public Transaction updateTransactionStatus(TransactionStatusUpdateDto transactionStatusUpdateDto) {
        Optional<Transaction >transactionOptional=transactionRepository.findByTransactionId(transactionStatusUpdateDto.getTransactionId());
        if(transactionOptional.isPresent())
        {
            Transaction transaction=transactionOptional.get();
            FundDetails fundDetails=fundDetailsRepository.getById(transactionStatusUpdateDto.getFundId());
            transaction.setFundDetails(fundDetails);
            transaction.setAmount(transactionStatusUpdateDto.getAmount());
            transaction.setNav(transactionStatusUpdateDto.getAmount());
            Customer customer=customerRepository.getById(transactionStatusUpdateDto.getCustomerId());
            transaction.setCustomer(customer);
            return transactionRepository.save(transaction);
        }
        else
        {
            throw new NotFoundException("Transtion is not found with given id :- " + transactionStatusUpdateDto.getTransactionId());

        }

    }

    @Override
    public List<FundManager> getAllFundManager() {
        return fundManagerRepository.findAll();
    }

  @Override
   public List<Transaction> getAllTransaction(String userEmail)
    {
          Optional<FundManager >fundManagerOptional=fundManagerRepository.findByEmail(userEmail);
          if(fundManagerOptional.isPresent())
          {
              FundManager fundManager=fundManagerOptional.get();
              return transactionRepository.findTransactionByTransactionStatusAndFundDetailsFundManager(TransactionStatus.Pending,fundManager);

          }
          else
          {
              throw new NotFoundException("Manager is not found with given email :- " + userEmail);

          }
    }

    @Override
    public FundManager getFundManager(String email) {
        Optional<FundManager> fundManagerOptional = fundManagerRepository.findByEmail(email);

        if (fundManagerOptional.isPresent()) {
            return fundManagerOptional.get();
        } else {
            throw new RuntimeException("Manager not found");
        }
    }

    @Override
    public ResponseEntity login(String email, String password) {
        return null;
    }




}
