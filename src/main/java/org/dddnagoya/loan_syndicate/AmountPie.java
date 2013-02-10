package org.dddnagoya.loan_syndicate;

/**
 * @author t_hara
 */
public class AmountPie extends SharePie {
    
    public AmountPie plus(AmountPie otherPie) {
        
        AmountPie newPie = new AmountPie();
        for (Share myShare : shares.values()) {
            Company owner = myShare.getOwner();
            if (otherPie.hasShare(owner)) {
                Share otherShare = otherPie.getShare(owner);
                // add
                newPie.putShare(myShare.addValue(otherShare.getValue()));
            } else {
                newPie.putShare(myShare);
            }
        }
        return newPie;
    }
    
    public AmountPie minus(AmountPie otherPie) {
        
        AmountPie newPie = new AmountPie();
        for (Share myShare : shares.values()) {
            Company owner = myShare.getOwner();
            if (otherPie.hasShare(owner)) {
                Share otherShare = otherPie.getShare(owner);
                // subtract 
                newPie.putShare(myShare.subtractValue(otherShare.getValue()));
            } else {
                newPie.putShare(myShare);
            }
        }
        return newPie;
    }
}
