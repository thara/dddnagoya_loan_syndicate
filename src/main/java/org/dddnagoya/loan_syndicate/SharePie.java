package org.dddnagoya.loan_syndicate;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * シェアパイ
 * @author t_hara
 */
public class SharePie {
    
    protected final Map<Company, Share> shares = new HashMap<>();
    
    private final MathContext prorationContext;
    
    public SharePie() {
        this(MathContext.DECIMAL128);
    }
    
    public SharePie(MathContext prorationContext) {
        super();
        if (prorationContext == null) throw new IllegalArgumentException("prorationContext must not be null.");
        
        this.prorationContext = prorationContext;
    }
    
    public SharePie(SharePie sharePie) {
        this.shares.putAll(sharePie.shares);
        this.prorationContext = sharePie.prorationContext;
    }

    /**
     * 指定されたShareを、このSharePieに追加する。
     * @param share Share
     */
    public void putShare(Share share) {
        if (share == null) throw new IllegalArgumentException("share must not be null.");
        shares.put(share.getOwner(), share);
    }
    
    /**
     * 指定された値を、このSharePieに含まれるShareに配分した新しいSharePieを返す
     * 
     * @param value 配分する値
     * @return 配分されたSharePie
     */
    public SharePie prorate(BigDecimal value) {
        if (value == null) throw new IllegalArgumentException("value must not be null.");
        
        BigDecimal sum = getSumOfShares();
        SharePie newPie = new SharePie(this.prorationContext);
        for (Share share : shares.values()) {
            BigDecimal prorated = value.multiply(share.getValue()).divide(sum, prorationContext);
            newPie.putShare(new Share(share.getOwner(), prorated));
        }
        return newPie;
    }

    /**
     * fromからtoの出資者に、valueで指定された値を移動する。
     * 
     * @param from 移動元
     * @param to 移動先
     * @param value 移動する値
     */
    public SharePie transfer(Company from, Company to, BigDecimal value) {
        if (from == null) throw new IllegalArgumentException();
        if (to == null) throw new IllegalArgumentException();
        if (value == null) throw new IllegalArgumentException();
        
        Share fromShare = shares.get(from);
        Share toShare = shares.get(to);
        
        if (fromShare == null) throw new IllegalArgumentException();
        if (toShare == null) throw new IllegalArgumentException();
        
        Share newFromShare = fromShare.subtractValue(value);
        if (newFromShare.getValue().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
        
        SharePie result = new SharePie(this);
        result.shares.put(from, newFromShare);
        result.shares.put(to, toShare.addValue(value));
        return result;
    }
    
    /**
     * @return 全ての出資者の変更不可なビュー
     */
    public Set<Company> getOwners() {
        return shares.keySet();
    }
    
    /**
     * 指定された出資者のShareを返す。
     * 
     * @param owner 出資者
     * @return Share
     */
    public Share getShare(Company owner) {
        return shares.get(owner);
    }
    
    /**
     * 指定された出資者のShareが、このSharePieに含まれている場合、trueを返す。
     * 
     * @param owner 出資者
     * @return 出資者がSharePieに含まれている場合、true
     */
    public boolean hasShare(Company owner) {
        return shares.containsKey(owner);
    }

    /**
     * このSharePieの値の合計値を返す。
     * 
     * @return 合計値
     */
    protected BigDecimal getSumOfShares() {
        BigDecimal sum = BigDecimal.ZERO;
        for (Share share : shares.values()) {
            sum = sum.add(share.getValue());
        }
        return sum;
    }
}
