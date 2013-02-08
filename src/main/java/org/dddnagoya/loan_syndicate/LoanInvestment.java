package org.dddnagoya.loan_syndicate;

import java.math.BigDecimal;

/**
 * ローン出資
 * <p>
 * 数値をラップして、金額としての演算メソッドを提供するValue Object.
 * </p>
 * @author t_hara
 */
public class LoanInvestment {
  
    /** 金額 */
    private final BigDecimal amount;
    
    /**
     * 金額0で、ローン出資を構築する。
     */
    public LoanInvestment() {
        this(BigDecimal.ZERO);
    }
    
    /**
     * 指定された金額で、ローン出資を構築する。
     * @param amount 金額
     */
    public LoanInvestment(BigDecimal amount) {
        super();
        this.amount = amount;
    }
    
    public BigDecimal toDecimal() {
        return this.amount;
    }
    
    /**
     * 指定されたローン出資を、このローン出資と加算した値を返す。
     * 
     * @param loanInvestment 加算するローン出資
     * @return 加算されたローン出資
     */
    public LoanInvestment add(LoanInvestment loanInvestment) {
        return new LoanInvestment(this.amount.add(loanInvestment.amount));
    }

    /**
     * 指定されたローン出資を、このローン出資と減算した値を返す。
     * 
     * @param loanInvestment 減算するローン出資
     * @return 減算されたローン出資
     */
    public LoanInvestment subtract(LoanInvestment loanInvestment) {
        return new LoanInvestment(this.amount.subtract(loanInvestment.amount));
    }
    
    /**
     * このローン出資の、指定された割合分にあたるローン出資を返す。
     * 
     * @param percentage 割合
     * @return 指定された割合分にあたるローン出資
     */
    public LoanInvestment divide(BigDecimal percentage) {
        return new LoanInvestment(this.amount.multiply(percentage.movePointLeft(2)));
    }
}
