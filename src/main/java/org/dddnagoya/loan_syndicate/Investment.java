package org.dddnagoya.loan_syndicate;

import java.math.BigDecimal;

/**
 * 出資
 * @author t_hara
 */
public class Investment {
    
    /** 出資者 */
    private final Company owner;
    
    /** 割合 */
    private BigDecimal percentage;
    
    /**
     * 指定された出資者と割合で、新規インスタンスを構築する。
     * @param owner 出資者
     * @param percentage 割合
     */
    public Investment(Company owner, BigDecimal percentage) {
        super();
        if (owner == null) throw new IllegalArgumentException("owner must not be null.");
        if (percentage == null) throw new IllegalArgumentException("percentage must not be null.");
        
        this.owner = owner;
        this.percentage = percentage;
    }
    
    /**
     * @return 出資者
     */
    public Company getOwner() {
        return owner;
    }
    
    /**
     * @return 割合
     */
    public BigDecimal getPercentage() {
        return percentage;
    }
}
