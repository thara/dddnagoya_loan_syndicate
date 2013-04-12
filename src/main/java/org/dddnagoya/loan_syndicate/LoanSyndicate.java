package org.dddnagoya.loan_syndicate;

import java.math.BigDecimal;

/**
 * ローンシンジケート
 * @author t_hara
 */
public class LoanSyndicate {

    /** ローン */
    private Loan lastLoan = new Loan();
    
    /** ファシリティ */
    private final Facility initialFacility;
    
    /**
     * 指定された初期ファシリティのローンシンジケートを構築する。
     * @param initialFacility 初期ファシリティ
     */
    public LoanSyndicate(Facility initialFacility) {
        super();
        this.initialFacility = initialFacility;
    }
    
    /**
     * 指定された額を、このローンシンジケートから引き出す。
     * <p>
     * 指定された額が、現在のファシリティの分担率に基づいて、各出資者に負担額が配分される。
     * </p>
     * 
     * @param amount 引き出す額
     */
    public Loan draw(BigDecimal amount) {
        return draw(amount, this.initialFacility);
    }
    
    public Loan draw(BigDecimal amount, Facility facility) {
        SharePie amountsProration = facility.getSharePie().prorate(amount);
        
        Loan loan = new Loan(this.lastLoan);
        for (Company owner : amountsProration.getOwners()) {
            Share share = amountsProration.getShare(owner);
            loan.adjustShare(owner, share.getValue());
        }
        return lastLoan = loan;
    }
    
    /**
     * 指定された額を元本として、出資者に支払う。
     * <p>
     * 指定された額が、ローンの分担率に基づいて、各出資者の未払いローン額が引かれる。
     * </p>
     * 
     * @param principalAmount 元本支払額
     */
    public Loan payPrincipal(BigDecimal principalAmount) {
        
        Loan loan = new Loan(this.lastLoan);
        
        SharePie principalsProration = loan.getSharePie().prorate(principalAmount);
        for (Company owner : principalsProration.getOwners()) {
            Share share = principalsProration.getShare(owner);
            loan.adjustShare(owner, share.getValue().negate());
        }
        
        return lastLoan = loan;
    }
    
    /**
     * 指定された手数料を支払う先の配分率を返す。
     * <p>
     * 手数料の支払は、常にファシリティの分担率に比例して配分される。
     * </p>
     * 
     * @param chargeAmount 手数料
     * @return 手数料の配分率
     */
    public SharePie getChargeProration(BigDecimal chargeAmount) {
        return initialFacility.getSharePie().prorate(chargeAmount);
    }

    /**
     * @return the facility
     */
    public Facility getInitialFacility() {
        return initialFacility;
    }
    
    /**
     * @return the loan
     */
    public Loan getLastLoan() {
        return lastLoan;
    }
}
