package org.dddnagoya.loan_syndicate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


/**
 * シェアパイ
 * @author t_hara
 */
public class SharePie {
    
    protected final Map<Company, Share> shares = new HashMap<>();
    
    public SharePie() {
        super();
    }
    
    public void addShare(Share share) {
        shares.put(share.getOwner(), share);
    }
    
    public SharePie prorate(BigDecimal value) {
        SharePie newPie = new SharePie();
        for (Share share : shares.values()) {
            newPie.addShare(share.addValue(value));
        }
        return newPie;
    }
    
    void transfer(Company from, Company to, BigDecimal value) {
        Share fromShare = shares.get(from);
        Share toShare = shares.get(to);
        shares.put(from, fromShare.subtractValue(value));
        shares.put(to, toShare.addValue(value));
    }
    
    public Share getShare(Company owner) {
        return shares.get(owner);
    }
    
    public boolean hasShare(Company owner) {
        return shares.containsKey(owner);
    }
}
