package org.dddnagoya.loan_syndicate;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * @author t_hara
 */
public class AmountPie extends SharePie {
    
    public AmountPie plus(SharePie otherPie) {
        
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
    
    public AmountPie minus(SharePie otherPie) {
        
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
