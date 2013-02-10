package org.dddnagoya.loan_syndicate;

import java.math.BigDecimal;

/**
 * 
 * @author t_hara
 */
public class PercentagePie extends SharePie {

    private static BigDecimal fullRate = new BigDecimal("100");
    
    @Override
    public void putShare(Share share) {
        
        BigDecimal sum = getSumOfShares();
        if (sum.add(share.getValue()).compareTo(fullRate) > 0) {
            throw new IllegalArgumentException();
        }
        
        super.putShare(share);
    }
}
