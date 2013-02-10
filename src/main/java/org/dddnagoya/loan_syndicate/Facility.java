package org.dddnagoya.loan_syndicate;

import java.math.BigDecimal;

/**
 * @author t_hara
 */
public class Facility {
    
    private BigDecimal limit;
    
    private SharePie sharePie = new PercentagePie();

    /**
     * @param limit
     */
    public Facility(BigDecimal limit) {
        super();
        this.limit = limit;
    }
    
    public void join(Company owner, BigDecimal percentage) {
        Share share = new Share(owner, percentage);
        sharePie.putShare(share);
    }
    
    public void transfer(Company from, Company to, BigDecimal percentage) {
        sharePie.transfer(from, to, percentage);
    }
    
    /**
     * @return the sharePie
     */
    public SharePie getSharePie() {
        return sharePie;
    }
    
    /**
     * @return the limit
     */
    public BigDecimal getLimit() {
        return limit;
    }
    
    public BigDecimal getPercentageOf(Company owner) {
        Share share = sharePie.getShare(owner);
        return share == null ? BigDecimal.ZERO : share.getValue();
    }
}