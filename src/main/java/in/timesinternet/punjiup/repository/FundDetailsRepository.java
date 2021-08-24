package in.timesinternet.punjiup.repository;
import in.timesinternet.punjiup.entity.FundDetails;
import in.timesinternet.punjiup.entity.FundManager;
import in.timesinternet.punjiup.entity.enumaration.FundType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface FundDetailsRepository extends JpaRepository<FundDetails, Integer> {
  List<FundDetails>  findAllByFundTypeAndFundManagerOrderByPreferenceAsc(FundType fundType, FundManager fundManager);
  FundDetails  findByFundIdAndFundManager(Integer fundId,FundManager fundManager);
    List<FundDetails> findAllByFundManagerOrderByPreferenceAsc(FundManager fundManager);
    List<FundDetails>  findAllByFundType(FundType fundType);
  List<FundDetails>  findAllByFundNameContainingIgnoreCase(String fundName);
}
