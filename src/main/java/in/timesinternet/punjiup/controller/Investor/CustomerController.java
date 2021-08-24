package in.timesinternet.punjiup.controller.Investor;
import in.timesinternet.punjiup.dto.*;
import in.timesinternet.punjiup.entity.Customer;
import in.timesinternet.punjiup.entity.CustomerFund;
import in.timesinternet.punjiup.entity.FundDetails;
import in.timesinternet.punjiup.entity.Transaction;
import in.timesinternet.punjiup.entity.enumaration.FundType;
import in.timesinternet.punjiup.service.UserService;
import in.timesinternet.punjiup.service.impl.BindingResultService;
import in.timesinternet.punjiup.service.impl.CustomerServiceImp;
import in.timesinternet.punjiup.service.impl.FundManagerServiceImpl;
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
@RequestMapping("api/investor")
public class CustomerController {
    @Autowired
    CustomerServiceImp customerServiceImp;
    @Autowired
    FundManagerServiceImpl fundManagerServiceImpl;
    @Autowired
    BindingResultService bindingResultService;
    @Autowired
    UserService userService;

    @PostMapping("/signup")
    ResponseEntity <Customer> createAccount(@RequestBody @Valid CustomerDto customerDto, BindingResult bindingResult) {

        bindingResultService.validate(bindingResult);
        Customer customer = customerServiceImp.createAccount(customerDto);
        return ResponseEntity.ok(customer);
    }

    @PostMapping("/login")
    ResponseEntity<HashMap<String,Object>> loginInvestor(@RequestBody @Valid LoginDto loginDto,BindingResult bindingResult)
    {
        bindingResultService.validate(bindingResult);
        return ResponseEntity.ok(userService.login(loginDto.getEmail(),loginDto.getPassword()));
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ROLE_INVESTOR')")
    ResponseEntity<Customer> updateInvestor(@RequestBody @Valid InvestorUpdateDto investorUpdateDto, HttpServletRequest httpServletRequest,BindingResult bindingResult) {
        //Update detail of users
        String userEmail=(String)httpServletRequest.getAttribute("userEmail");
        Customer customer=customerServiceImp.updateCustomer(investorUpdateDto,userEmail);
        return ResponseEntity.ok(customer);
    }


    @GetMapping("/fund")
    @PreAuthorize("hasRole('ROLE_INVESTOR')")
    ResponseEntity<List<FundDetails>> getAllFunds() {

        return ResponseEntity.ok(customerServiceImp.getAllFunds());
    }

    @GetMapping("/{FundId}/fund")
    @PreAuthorize("hasRole('ROLE_INVESTOR')")
    ResponseEntity<FundDetails> getFund(@PathVariable @Valid int FundId) {
        //Return particular Fund;
        return ResponseEntity.ok(customerServiceImp.getFund(FundId));
    }

    @GetMapping("/fund/{fundType}")
    @PreAuthorize("hasRole('ROLE_INVESTOR')")
   ResponseEntity <List<FundDetails>> getAllTypeFund(@PathVariable FundType fundType) {
        //get all open or close end fund;
        return ResponseEntity.ok(customerServiceImp.getAllTypeFund(fundType));
    }




    @PostMapping("/cart/addItem")
    @PreAuthorize("hasRole('ROLE_INVESTOR')")
   ResponseEntity <Transaction> addFundCart(@RequestBody @Valid TransactionDto transactionDto,HttpServletRequest httpServletRequest) {
        String userEmail=(String)httpServletRequest.getAttribute("userEmail");
        return ResponseEntity.ok(customerServiceImp.startTransaction(transactionDto,userEmail));
    }

    @GetMapping("/{customerId}/cart")
    @PreAuthorize("hasRole('ROLE_INVESTOR')")
    ResponseEntity<List<Transaction>>showCart(@PathVariable int customerId) {

        return ResponseEntity.ok(customerServiceImp.showCart(customerId));
    }


    @DeleteMapping("/cart/delete")
    @PreAuthorize("hasRole('ROLE_INVESTOR')")
    ResponseEntity<String> deleteCartItem(@RequestBody DeleteCartItemDto deleteCartItemDto) {
        return ResponseEntity.ok(customerServiceImp.deleteTransaction(deleteCartItemDto));
    }

    @GetMapping("/")
    Object customerPositionDetail(@PathVariable CustomerFund customerFund) {
        return null;
    }

    @PostMapping("/buycart")
    @PreAuthorize("hasRole('ROLE_INVESTOR')")
    ResponseEntity<List<Transaction>> buy(@RequestBody BuyDto buyDto){
        return ResponseEntity.ok(customerServiceImp.buyCart(buyDto));
    }

    @PostMapping("/buy")
    @PreAuthorize("hasRole('ROLE_INVESTOR')")
    ResponseEntity<Transaction> buy(@RequestBody TransactionDto transactionDto,HttpServletRequest httpServletRequest){
        String userEmail=(String)httpServletRequest.getAttribute("userEmail");
        return ResponseEntity.ok(customerServiceImp.buy(transactionDto,userEmail));
    }
}