package org.dddnagoya.loan_syndicate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * ローン
 * @author t_hara
 */
public class Loan {
    
    /** ファシリティ */
    private final Facility facility;
    
    /** 出資とローン出資のMap */
    private Map<Investment, LoanInvestment> loanInvestments = new HashMap<>();
    
    /** 総額 */
    private BigDecimal sum = BigDecimal.ZERO;

    /**
     * 指定されたファシリティにおけるローンを構築する。
     * 
     * @param facility
     */
    public Loan(Facility facility) {
        super();
        this.facility = facility;
        for (Investment investment : facility.getInvestments()) {
            loanInvestments.put(investment, new LoanInvestment());
        }
    }

    public void increase(LoanInvestment amount) {

        BigDecimal sum = this.sum.add(amount.toDecimal());
        if (facility.getLimit().compareTo(sum) < 0) {
            throw new IllegalArgumentException("the amount make this sum to be over limit.");
        }
        this.sum = sum;
        
        for (Entry<Investment, LoanInvestment> entry : loanInvestments.entrySet()) {
            Investment investment = entry.getKey();
            BigDecimal percentage = investment.getPercentage();
            
            LoanInvestment value = entry.getValue();
            LoanInvestment increment = amount.divide(percentage);
            entry.setValue(value.add(increment));
        }
    }
    
    public void decrease(LoanInvestment amount) {
        
        BigDecimal sum = this.sum.subtract(amount.toDecimal());
        if (sum.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("the amount make this sum to be less than zero.");
        }
        this.sum = sum;
        
        for (Entry<Investment, LoanInvestment> entry : loanInvestments.entrySet()) {
            Investment investment = entry.getKey();
            BigDecimal percentage = investment.getPercentage();
            
            LoanInvestment value = entry.getValue();
            LoanInvestment decrement = amount.divide(percentage);
            entry.setValue(value.subtract(decrement));
        }
    }
    
    public LoanInvestment getLoanInvestment(Company owner) {
        for (Entry<Investment, LoanInvestment> entry : loanInvestments.entrySet()) {
            if (entry.getKey().getOwner().equals(owner)) {
                return entry.getValue();
            }
        }
        throw new IllegalArgumentException("the owner hasn't join this facility yet.");
    }
    
    /**
     * @return the sum
     */
    public BigDecimal getSum() {
        return sum;
    }
}
