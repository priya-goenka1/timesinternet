package in.timesinternet.punjiup.controller.Manager;
import in.timesinternet.punjiup.dto.*;
import in.timesinternet.punjiup.entity.FundDetails;
import in.timesinternet.punjiup.entity.FundManager;
import in.timesinternet.punjiup.entity.Transaction;
import in.timesinternet.punjiup.entity.enumaration.FundType;
import in.timesinternet.punjiup.entity.enumaration.TransactionStatus;
import in.timesinternet.punjiup.repository.FundDetailsRepository;
import in.timesinternet.punjiup.repository.FundManagerRepository;
import in.timesinternet.punjiup.repository.TransactionRepository;
import in.timesinternet.punjiup.service.FundManagerService;
import in.timesinternet.punjiup.service.UserService;
import in.timesinternet.punjiup.service.impl.BindingResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
@RestController
@RequestMapping("/api/fundmanager")
public class ManagerController {
    @Autowired
    FundDetailsRepository fundDetailsRepository;
    @Autowired
    FundManagerRepository fundManagerRepository;
    @Autowired
    FundManagerService fundManagerService;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    UserService userService;
    @Autowired
    BindingResultService bindingResultService;
    @PostMapping("/login")
    ResponseEntity<HashMap<String, Object>> loginManager(@RequestBody @Valid  LoginDto loginDto, BindingResult bindingResult)
    {
        bindingResultService.validate(bindingResult);

        return ResponseEntity.ok(userService.login(loginDto.getEmail(),loginDto.getPassword()));
    }
    @PostMapping("/signup")
    ResponseEntity<FundManager> createManager(@RequestBody  @Valid FundmanagerDto fundmanagerDto, BindingResult bindingResult)
    {
        //Manager Service Call;
        bindingResultService.validate(bindingResult);
        FundManager fundManager=fundManagerService.createFundManagerAccount(fundmanagerDto);
        return ResponseEntity.ok(fundManager);
    }
    @GetMapping("/allfundmanager")
    ResponseEntity<List<FundManager> >fundManagerList()
    {
        return ResponseEntity.ok(fundManagerService.getAllFundManager());
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ROLE_FUNDMANAGAER')")
    ResponseEntity<FundManager >updateManager(@RequestBody @Valid  FundManagerUpdateDTO fundManagerUpdateDTO,HttpServletRequest httpServletRequest,BindingResult bindingResult)
    {
        bindingResultService.validate(bindingResult);
        String userEmail=(String) httpServletRequest.getAttribute("userEmail");
        FundManager fundManager=fundManagerService.UpdateManager(fundManagerUpdateDTO,userEmail);
        return  ResponseEntity.ok(fundManager);
    }
    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_FUNDMANAGER')")
    ResponseEntity<FundManager> getFundManager(HttpServletRequest httpServletRequest){
        String userEmail =(String) httpServletRequest.getAttribute("userEmail");
        return ResponseEntity.ok(fundManagerService.getFundManager(userEmail));
    }
    @PostMapping("/addfund")
    @PreAuthorize("hasRole('ROLE_FUNDMANAGAER')")
   ResponseEntity< FundDetails> addFund(@RequestBody @Valid  FundDto fundDto,HttpServletRequest httpServletRequest)
    {
        //update Mangaer Service Call
        String userEmail=(String) httpServletRequest.getAttribute("userEmail");
       FundDetails fundDetails=fundManagerService.addFund(fundDto,userEmail);
      return ResponseEntity.ok(fundDetails);
    }
    @PutMapping("/fundUpdate")
    @PreAuthorize("hasRole('ROLE_FUNDMANAGAER')")
  ResponseEntity< FundDetails >fundUpdate(@RequestBody FundUpdateDto fundUpdateDto)
    {
        //update Mangaer Service Call
        FundDetails fundDetails=fundManagerService.fundUpdate(fundUpdateDto);
        return ResponseEntity.ok(fundDetails) ;
    }
    @GetMapping("/funds")
    @PreAuthorize("hasRole('ROLE_FUNDMANAGAER')")
    ResponseEntity< List<FundDetails> >getAllFund(HttpServletRequest httpServletRequest)
    {
        //Get All funds for perticular manager
        String userEmail=(String) httpServletRequest.getAttribute("userEmail");
        List<FundDetails>fundDetailsList=fundManagerService.getAllFund(userEmail);
        return ResponseEntity.ok(fundDetailsList);
    }
    @GetMapping("/fund/{fundId}")
    @PreAuthorize("hasRole('ROLE_FUNDMANAGAER')")
   ResponseEntity< FundDetails> getFund(@PathVariable Integer fundId,HttpServletRequest httpServletRequest)
    {
        //update Mangaer Service Call
        String userEmail=(String) httpServletRequest.getAttribute("userEmail");
        FundDetails fundDetails=fundManagerService.getFund(fundId,userEmail);
        return ResponseEntity.ok(fundDetails);
    }
    @GetMapping("/funds/{fundType}")
    @PreAuthorize("hasRole('ROLE_FUNDMANAGAER')")
    ResponseEntity<List<FundDetails >>getAllTypeFund(@PathVariable FundType fundType, HttpServletRequest httpServletRequest)
    {
        //get all open or close end fund;
        String userEmail=(String) httpServletRequest.getAttribute("userEmail");
        List<FundDetails> fundDetailsList=fundManagerService.getAllTypeFund(fundType,userEmail);
        return ResponseEntity.ok(fundDetailsList);
    }
  // To update Transaction Status
    @PutMapping("/fund/updatetransaction")
    @PreAuthorize("hasRole('ROLE_FUNDMANAGAER')")
    ResponseEntity<Transaction >updateTransactinStatus(@RequestBody TransactionStatusUpdateDto transactionStatusUpdateDto)
    {
        return ResponseEntity.ok(fundManagerService.updateTransactionStatus(transactionStatusUpdateDto));
    }

   // To get all Unapproved Transaction
    @GetMapping("/{managerId}/fund/transactions/{transactionStatus}")
    ResponseEntity<List<Transaction>> getTransaction(@PathVariable Integer managerId,@PathVariable TransactionStatus transactionStatus)
    {
        FundManager fundManager=fundManagerRepository.getById(managerId);
        return ResponseEntity.ok(transactionRepository.findTransactionByTransactionStatusAndFundDetailsFundManager(transactionStatus,fundManager));
    }
    @GetMapping("/fund/transactions")
    ResponseEntity<List<Transaction>>getAllTransaction(HttpServletRequest httpServletRequest)
    {
        String userEmail=(String) httpServletRequest.getAttribute("userEmail");
        return ResponseEntity.ok(fundManagerService.getAllTransaction(userEmail));
    }


}
