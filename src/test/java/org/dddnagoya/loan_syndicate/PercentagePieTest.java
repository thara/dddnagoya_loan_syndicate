package org.dddnagoya.loan_syndicate;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

/**
 * @author t_hara
 */
public class PercentagePieTest {

    static final Company owner_A = new Company(1);
    static final Company owner_B = new Company(2);
    static final Company owner_C = new Company(3);
    static final Company owner_D = new Company(4);

    PercentagePie sut;
    
    @Before
    public void setUp() {
        sut = new PercentagePie();
        
        sut.putShare(new Share(owner_A, new BigDecimal("50")));
        sut.putShare(new Share(owner_B, new BigDecimal("20")));
        sut.putShare(new Share(owner_C, new BigDecimal("30")));
        
        assert sut.getSumOfShares().equals(new BigDecimal("100")) : "100以下ならOK";
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void addShareを呼んだ時に合計が100を超えた場合に例外を発生する() {
        
        sut.putShare(new Share(owner_D, new BigDecimal("1")));
    }
}
