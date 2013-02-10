package org.dddnagoya.loan_syndicate;

import java.math.BigDecimal;

/**
 * ローン
 * @author t_hara
 */
public class Loan {
    
    /** 総額パイ */
    private AmountPie sharePie = new AmountPie();
    
    public Loan() {
        super();
    }
    
    /**
     * 指定された出資者と額を、このローンに適用する。
     * 
     * @param owner 出資者
     * @param amount 額
     */
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
    
//    public void increase(BigDecimal amount) {
//        SharePie propartion = this.sharePie.prorate(amount);
//        this.sharePie = this.sharePie.plus(new AmountPie(propartion));
//    }
//    
//    public void decrease(BigDecimal amount) {
//        SharePie propartion = this.sharePie.prorate(amount);
//        this.sharePie = this.sharePie.minus(new AmountPie(propartion));
//    }
    
    /**
     * 指定された出資者のローン総額を返す。
     * 
     * @param owner 出資者
     * @return ローン総額
     */
    public BigDecimal getAmountBy(Company owner) {
        Share share = sharePie.getShare(owner);
        return (share == null) ? BigDecimal.ZERO : share.getValue();
    }
    
    /**
     * @return 総額パイ
     */
    public AmountPie getSharePie() {
        return sharePie;
    }
    
    /**
     * @return 合計値
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
