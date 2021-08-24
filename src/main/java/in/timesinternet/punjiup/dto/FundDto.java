package in.timesinternet.punjiup.dto;

import in.timesinternet.punjiup.entity.embeddable.CloseEndFund;
import in.timesinternet.punjiup.entity.enumaration.FundType;
import in.timesinternet.punjiup.entity.enumaration.IsActive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FundDto {
    private String fundName;
    private String symbol;
    private Double totalValue;
    private Double nav;
    private Double expenseRatio;
    private Integer preference;
    private FundType fundType;
    private Double exitLoad;
    private CloseEndFund closeEndFund;
}
