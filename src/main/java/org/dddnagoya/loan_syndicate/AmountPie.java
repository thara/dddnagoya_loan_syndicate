package org.dddnagoya.loan_syndicate;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * 総額パイ
 * @author t_hara
 */
public class AmountPie extends SharePie {
    
    public AmountPie() {
        super();
    }
    
    /**
     * 指定されたSharePieを元にした、新しいAmoutPieを構築する。
     * 
     * @param sharePie 元となるSharePie
     */
    public AmountPie(SharePie sharePie) {
        super();
        for (Company owner : sharePie.getOwners()) {
            Share share = sharePie.getShare(owner);
            putShare(share);
        }
    }
    
    /**
     * 指定されたotherPieと、このAmoutPieを加算した新しいAmoutPieを返す。
     * 
     * @param otherPie 加算するSharePie
     * @return 加算した新しいAmoutPie
     */
    public AmountPie plus(AmountPie otherPie) {
        
        AmountPie newPie = new AmountPie();
        
        Set<Company> owners = new HashSet<>();
        owners.addAll(this.getOwners());
        owners.addAll(otherPie.getOwners());
        
        for (Company owner : owners) {
            Share myShare = getShare(owner);
            Share otherShare = otherPie.getShare(owner);
            
            BigDecimal myAmount = (myShare == null) ? BigDecimal.ZERO : myShare.getValue();
            BigDecimal otherAmount = (otherShare == null) ? BigDecimal.ZERO : otherShare.getValue();
            
            newPie.putShare(new Share(owner, myAmount.add(otherAmount)));
        }
        
        return newPie;
    }
    
    /**
     * 指定されたotherPieと、このAmoutPieを減算した新しいAmoutPieを返す。
     * 
     * @param otherPie 減算するSharePie
     * @return 減算した新しいAmoutPie
     */
    public AmountPie minus(AmountPie otherPie) {
        
        AmountPie newPie = new AmountPie();
        
        Set<Company> owners = new HashSet<>();
        owners.addAll(this.getOwners());
        owners.addAll(otherPie.getOwners());
        
        for (Company owner : owners) {
            Share myShare = getShare(owner);
            Share otherShare = otherPie.getShare(owner);
            
            BigDecimal myAmount = (myShare == null) ? BigDecimal.ZERO : myShare.getValue();
            BigDecimal otherAmount = (otherShare == null) ? BigDecimal.ZERO : otherShare.getValue();
            
            newPie.putShare(new Share(owner, myAmount.subtract(otherAmount)));
        }
        
        return newPie;
    }
}
