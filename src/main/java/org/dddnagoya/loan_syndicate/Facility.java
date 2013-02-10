package org.dddnagoya.loan_syndicate;

import java.math.BigDecimal;

/**
 * ファシリティ
 * @author t_hara
 */
public class Facility {
    
    /** 限界額 */
    private BigDecimal limit;
    
    /** 割合パイ */
    private PercentagePie sharePie = new PercentagePie();

    /**
     * 指定された限界額で、ファシリティを構築する。
     * 
     * @param limit 限界額
     */
    public Facility(BigDecimal limit) {
        super();
        if (limit == null) throw new IllegalArgumentException();
        this.limit = limit;
    }
    
    /**
     * 指定された出資者を、指定された割合で、このファシリティに参加させる。
     * 
     * @param owner 出資者
     * @param percentage 割合
     */
    public void join(Company owner, BigDecimal percentage) {
        Share share = new Share(owner, percentage);
        sharePie.putShare(share);
    }
    
    /**
     * @see {@link SharePie#transfer(Company, Company, BigDecimal)}
     */
    public void transfer(Company from, Company to, BigDecimal percentage) {
        sharePie.transfer(from, to, percentage);
    }
    
    /**
     * @return 割合パイ
     */
    public PercentagePie getSharePie() {
        return sharePie;
    }
    
    /**
     * @return 限界額
     */
    public BigDecimal getLimit() {
        return limit;
    }
    
    /**
     * 指定された出資者の、このファシリティにおける割合を返す。
     * 
     * @param owner 出資者
     * @return 割合
     */
    public BigDecimal getPercentageOf(Company owner) {
        Share share = sharePie.getShare(owner);
        return share == null ? BigDecimal.ZERO : share.getValue();
    }
}