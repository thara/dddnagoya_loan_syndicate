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
    
    public void prorate(BigDecimal value) {
        for (Share share : shares.values()) {
            addShare(share.addValue(value));
        }
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
