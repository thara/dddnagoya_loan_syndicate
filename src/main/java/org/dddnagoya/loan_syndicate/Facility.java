package org.dddnagoya.loan_syndicate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * ファシリティ
 * <p>
 * ローンにおける、融資する側の企業による確約を表す。
 * </p>
 * @author t_hara
 */
public class Facility {

    //TODO 本来のソフトウェアシステムなら、このクラスに識別子を持たせてエンティティにする。
    
    /** 100% */
    private static final BigDecimal fullRate = new BigDecimal("100");
    
    /** 限度額 */
    private final BigDecimal limit;
    
    /** このファシリティにおける出資のList */
    private final List<Investment> investments = new ArrayList<>();

    /**
     * 指定された限度額までの融資を表すファシリティを構築する。
     * 
     * @param limit 限度額
     */
    public Facility(BigDecimal limit) {
        super();
        if (limit == null) throw new IllegalArgumentException("limit must not be null.");
        this.limit = limit;
    }
    
    /**
     * @return 限度額
     */
    public BigDecimal getLimit() {
        return limit;
    }
    
    /**
     * @return このファシリティにおける出資のListのビュー
     */
    public List<Investment> getInvestments() {
        return new ArrayList<>(investments);
    }
    
    /**
     * 指定された出資者をこのファシリティに参加させる。
     * <p>
     * 渡されたInvestmentの出資者は、このファイリティにおいて、指定された分担率で融資する。
     * </p>
     * @param investment
     */
    public void join(Investment investment) {
        BigDecimal percentage = investment.getPercentage();
        if (fullRate.compareTo(getRatio().add(percentage)) < 0) {
            throw new IllegalArgumentException();
        }
        this.investments.add(investment);
    }
    
    /**
     * @return 割合
     */
    BigDecimal getRatio() {
        BigDecimal total = BigDecimal.ZERO;
        for (Investment investment : this.investments) {
            total = total.add(investment.getPercentage());
        }
        return total;
    }
}
