
package in.timesinternet.punjiup.controller.General;
import in.timesinternet.punjiup.entity.FundDetails;
import in.timesinternet.punjiup.entity.FundManager;
import in.timesinternet.punjiup.entity.enumaration.FundType;
import in.timesinternet.punjiup.service.impl.CustomerServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/home")
public class GeneralController {
    @Autowired
    CustomerServiceImp customerServiceImp;
    @GetMapping("/fund")
    List<FundDetails> getAllFunds()
    {
        return customerServiceImp.getAllFunds();
    }


    @GetMapping("/fund/{fundType}")
    List<FundDetails> getAllTypeFund(@PathVariable FundType fundType)
    {
        return customerServiceImp.getAllTypeFund(fundType);
    }


    @GetMapping("/{fundId}/fund")
    FundDetails getFund(@PathVariable int fundId)
    {
        return customerServiceImp.getFund(fundId);
    }

    @GetMapping("/fundList")
    List<FundDetails> getFundBySearch(@RequestParam(name = "name", required = true) String search) {
        return customerServiceImp.getFundsBySearch(search);
    }
    @GetMapping("/fundManager")
    List<FundManager> getFundManagerBySearch(@RequestParam(name = "name", required = true) String search){
        return customerServiceImp.getFundManagerBySearch(search,search);
    }


}




