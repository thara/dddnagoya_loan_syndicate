package org.dddnagoya.loan_syndicate;

import java.math.BigDecimal;

/**
 * @author t_hara
 */
public class Loan {
    
    private AmountPie sharePie = new AmountPie();
    
    /**
     * @param initialFacility
     */
    public Loan() {
        super();
    }
    
    public void adjustShare(Company owner, BigDecimal amount) {
        Share newShare;
        if (sharePie.hasShare(owner)) {
            Share share = sharePie.getShare(owner);
            newShare = share.addValue(amount);
        } else {
            newShare = new Share(owner, amount);
        }
        sharePie.putShare(newShare);
    }
    
    public void increase(BigDecimal amount) {
        SharePie propartion = this.sharePie.prorate(amount);
        this.sharePie = this.sharePie.plus(propartion);
    }
    
    public void decrease(BigDecimal amount) {
        SharePie propartion = this.sharePie.prorate(amount);
        this.sharePie = this.sharePie.minus(propartion);
    }
    
    public BigDecimal getAmountBy(Company owner) {
        Share share = sharePie.getShare(owner);
        return (share == null) ? BigDecimal.ZERO : share.getValue();
    }
    
    /**
     * @return the sharePie
     */
    public AmountPie getSharePie() {
        return sharePie;
    }
    
    /**
     * @return the sum
     */
    public BigDecimal getAmount() {
        BigDecimal amount = BigDecimal.ZERO;
        for (Company owner : sharePie.getOwners()) {
            Share share = sharePie.getShare(owner);
            amount = amount.add(share.getValue());
        }
        return amount;
    }    
}
