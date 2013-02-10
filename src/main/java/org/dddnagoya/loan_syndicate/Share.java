package org.dddnagoya.loan_syndicate;

import java.math.BigDecimal;


/**
 * シェア
 * @author t_hara
 */
public class Share {

    /** 出資者 */
    private final Company owner;
    
    /** 値 */
    private final BigDecimal value;

    /**
     * 指定された出資者と値で、Shareを構築する。
     * 
     * @param owner 出資者
     * @param value 値
     */
    public Share(Company owner, BigDecimal value) {
        super();
        if (owner == null) throw new IllegalArgumentException();
        if (value == null) throw new IllegalArgumentException();
        
        this.owner = owner;
        this.value = value;
    }
    
    /**
     * @return 出資者
     */
    public Company getOwner() {
        return owner;
    }
    
    /**
     * @return 値
     */
    public BigDecimal getValue() {
        return value;
    }
    
    /**
     * 指定された値と、このShareの値を加算した新しいShareを返す。
     * 
     * @param value 加算する値
     * @return 加算した新しいShare
     */
    public Share addValue(BigDecimal value) {
        return new Share(this.owner, this.value.add(value));
    }
    
    /**
     * 指定された値と、このShareの値を減算した新しいShareを返す。
     * 
     * @param value 減算する値
     * @return 減算した新しいShare
     */
    public Share subtractValue(BigDecimal value) {
        return new Share(this.owner, this.value.subtract(value));
    }
    
    @Override
    public String toString() {
        return "Share[" + owner + ":" + value.toPlainString() + "]";
    }
}
