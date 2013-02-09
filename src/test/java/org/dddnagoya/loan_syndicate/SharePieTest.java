package org.dddnagoya.loan_syndicate;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * @author t_hara
 */
@RunWith(Enclosed.class)
public class SharePieTest {

    static final Company owner_A = new Company(1);
    static final Company owner_B = new Company(2);
    static final Company owner_C = new Company(3);
    
    public static class ProrationTest {
        @Test
        public void prorateに正の値を渡すと全てのShareのvalueにその値が加算される() {
            SharePie sut = new SharePie();
            
            sut.addShare(new Share(owner_A, new BigDecimal("10")));
            sut.addShare(new Share(owner_B, new BigDecimal("20")));
            sut.addShare(new Share(owner_C, new BigDecimal("30")));
            
            sut.prorate(new BigDecimal("1.5"));
            
            assertThat(sut.getShare(owner_A).getValue(), is(new BigDecimal("11.5")));
            assertThat(sut.getShare(owner_B).getValue(), is(new BigDecimal("21.5")));
            assertThat(sut.getShare(owner_C).getValue(), is(new BigDecimal("31.5")));
        }
        
        @Test
        public void prorateに負の値を渡すと全てのShareのvalueからその値が減算される() {
            SharePie sut = new SharePie();
            
            sut.addShare(new Share(owner_A, new BigDecimal("10")));
            sut.addShare(new Share(owner_B, new BigDecimal("20")));
            sut.addShare(new Share(owner_C, new BigDecimal("30")));
            
            sut.prorate(new BigDecimal("-3.2"));
            
            assertThat(sut.getShare(owner_A).getValue(), is(new BigDecimal("6.8")));
            assertThat(sut.getShare(owner_B).getValue(), is(new BigDecimal("16.8")));
            assertThat(sut.getShare(owner_C).getValue(), is(new BigDecimal("26.8")));
        }
    }
    
    public static class TransferalTest {
        
        @Test
        public void transferによってCompany間のShareのvalueを移行できる() {
            SharePie sut = new SharePie();
            
            sut.addShare(new Share(owner_A, new BigDecimal("10")));
            sut.addShare(new Share(owner_B, new BigDecimal("20")));
            sut.addShare(new Share(owner_C, new BigDecimal("30")));
            
            sut.transfer(owner_A, owner_B, new BigDecimal("6"));
            
            assertThat(sut.getShare(owner_A).getValue(), is(new BigDecimal("4")));
            assertThat(sut.getShare(owner_B).getValue(), is(new BigDecimal("26")));
            
            assertThat("must not be change owner_C's value",
                    sut.getShare(owner_C).getValue(), is(new BigDecimal("30")));
            
            sut.transfer(owner_A, owner_C, new BigDecimal("2"));
            
            assertThat(sut.getShare(owner_A).getValue(), is(new BigDecimal("2")));
            assertThat(sut.getShare(owner_C).getValue(), is(new BigDecimal("32")));
            
            assertThat("must not be change owner_B's value",
                    sut.getShare(owner_C).getValue(), is(new BigDecimal("32")));
        }
    }
    
}
