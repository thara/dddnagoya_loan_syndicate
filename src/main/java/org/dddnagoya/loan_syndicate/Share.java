package org.dddnagoya.loan_syndicate;

import java.math.BigDecimal;


/**
 * シェア
 * @author t_hara
 */
public class Share {

    private final Company owner;
    
    private final BigDecimal value;

    /**
     * @param owner
     * @param value
     */
    public Share(Company owner, BigDecimal value) {
        super();
        this.owner = owner;
        this.value = value;
    }
    
    /**
     * @return the owner
     */
    public Company getOwner() {
        return owner;
    }
    
    /**
     * @return the value
     */
    public BigDecimal getValue() {
        return value;
    }
    
    public Share addValue(BigDecimal value) {
        return new Share(this.owner, this.value.add(value));
    }
    
    public Share subtractValue(BigDecimal value) {
        return new Share(this.owner, this.value.subtract(value));
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Share[" + owner + ":" + value.toPlainString() + "]";
    }
}
