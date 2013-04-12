package org.dddnagoya.loan_syndicate;

import java.math.BigDecimal;

/**
 * 割合パイ
 * @author t_hara
 */
public class PercentagePie extends SharePie {

    private static BigDecimal fullRate = new BigDecimal("100");
    
    public PercentagePie() {
        super();
    }
    
    /**
     * @param sharePie
     */
    public PercentagePie(SharePie sharePie) {
        super(sharePie);
    }

    @Override
    public void putShare(Share share) {
        
        BigDecimal sum = getSumOfShares();
        if (sum.add(share.getValue()).compareTo(fullRate) > 0) {
            throw new IllegalArgumentException();
        }
        
        super.putShare(share);
    }
}
