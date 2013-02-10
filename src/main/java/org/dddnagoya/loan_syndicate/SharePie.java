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
    
    private MathContext prorationContext = MathContext.DECIMAL128;
    
    public SharePie() {
        super();
    }
    
    public SharePie(MathContext prorationContext) {
        super();
        this.prorationContext = prorationContext;
    }

    public void putShare(Share share) {
        shares.put(share.getOwner(), share);
    }
    
    public SharePie prorate(BigDecimal value) {
        BigDecimal sum = getSumOfShares();
        SharePie newPie = new SharePie();
        for (Share share : shares.values()) {
            BigDecimal prorated = value.multiply(share.getValue()).divide(sum, prorationContext);
            newPie.putShare(new Share(share.getOwner(), prorated));
        }
        return newPie;
    }

    void transfer(Company from, Company to, BigDecimal value) {
        Share fromShare = shares.get(from);
        Share toShare = shares.get(to);
        shares.put(from, fromShare.subtractValue(value));
        shares.put(to, toShare.addValue(value));
    }
    
    public Set<Company> getOwners() {
        return shares.keySet();
    }
    
    public Share getShare(Company owner) {
        return shares.get(owner);
    }
    
    public boolean hasShare(Company owner) {
        return shares.containsKey(owner);
    }

    /**
     * @return
     */
    protected BigDecimal getSumOfShares() {
        BigDecimal sum = BigDecimal.ZERO;
        for (Share share : shares.values()) {
            sum = sum.add(share.getValue());
        }
        return sum;
    }
}
