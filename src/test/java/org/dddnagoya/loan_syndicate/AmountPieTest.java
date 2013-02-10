package org.dddnagoya.loan_syndicate;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * @author t_hara
 */
@RunWith(Enclosed.class)
public class AmountPieTest {
    
    static final Company owner_A = new Company(1);
    static final Company owner_B = new Company(2);
    static final Company owner_C = new Company(3);

    static AmountPie sut;
    
    @BeforeClass
    public static void init() {
        sut = new AmountPie();
        sut.addShare(new Share(owner_A, new BigDecimal("98.09")));
        sut.addShare(new Share(owner_B, new BigDecimal("31.91")));
        sut.addShare(new Share(owner_C, new BigDecimal("75.00")));
    }
    
    public static class PlusTest {
        @Test
        public void calleeとparameterの状態を変更しない() {
            AmountPie param = new AmountPie();
            param.addShare(new Share(owner_A, new BigDecimal("10.01")));
            param.addShare(new Share(owner_B, new BigDecimal("20.00")));
            param.addShare(new Share(owner_C, new BigDecimal("15.00")));
            
            sut.plus(param);
            
            assertThat(param.getShare(owner_A).getValue(), is(new BigDecimal("10.01")));
            assertThat(param.getShare(owner_B).getValue(), is(new BigDecimal("20.00")));
            assertThat(param.getShare(owner_C).getValue(), is(new BigDecimal("15.00")));
            
            assertThat(sut.getShare(owner_A).getValue(), is(new BigDecimal("98.09")));
            assertThat(sut.getShare(owner_B).getValue(), is(new BigDecimal("31.91")));
            assertThat(sut.getShare(owner_C).getValue(), is(new BigDecimal("75.00")));
        }
        
        @Test
        public void calleeとparameterのshareの値を加算した値を返す() {
            AmountPie param = new AmountPie();
            param.addShare(new Share(owner_A, new BigDecimal("10.01")));
            param.addShare(new Share(owner_B, new BigDecimal("59.09")));
            param.addShare(new Share(owner_C, new BigDecimal("15.48")));
            
            AmountPie result = sut.plus(param);
            
            assertThat(result.getShare(owner_A).getValue(), is(new BigDecimal("108.10")));
            assertThat(result.getShare(owner_B).getValue(), is(new BigDecimal("91.00")));
            assertThat(result.getShare(owner_C).getValue(), is(new BigDecimal("90.48")));
        }
        
        @Test
        public void parameterに存在しないshareの値には影響を与えない() {
            AmountPie param = new AmountPie();
            param.addShare(new Share(owner_A, new BigDecimal("10.01")));
            param.addShare(new Share(owner_C, new BigDecimal("15.48")));
            
            AmountPie result = sut.plus(param);
            
            assertThat(result.getShare(owner_A).getValue(), is(new BigDecimal("108.10")));
            assertThat("this value must not be change.", result.getShare(owner_B).getValue(), is(new BigDecimal("31.91")));
            assertThat(result.getShare(owner_C).getValue(), is(new BigDecimal("90.48")));
        }
    }
    
    public static class MinusTest {
        
        @Test
        public void calleeとparameterの状態を変更しない() {
            AmountPie param = new AmountPie();
            param.addShare(new Share(owner_A, new BigDecimal("10.01")));
            param.addShare(new Share(owner_B, new BigDecimal("20.00")));
            param.addShare(new Share(owner_C, new BigDecimal("15.00")));
            
            sut.minus(param);
            
            assertThat(param.getShare(owner_A).getValue(), is(new BigDecimal("10.01")));
            assertThat(param.getShare(owner_B).getValue(), is(new BigDecimal("20.00")));
            assertThat(param.getShare(owner_C).getValue(), is(new BigDecimal("15.00")));
            
            assertThat(sut.getShare(owner_A).getValue(), is(new BigDecimal("98.09")));
            assertThat(sut.getShare(owner_B).getValue(), is(new BigDecimal("31.91")));
            assertThat(sut.getShare(owner_C).getValue(), is(new BigDecimal("75.00")));
        }
        
        @Test
        public void calleeとparameterのshareの値を加算した値を返す() {
            AmountPie param = new AmountPie();
            param.addShare(new Share(owner_A, new BigDecimal("13.04")));
            param.addShare(new Share(owner_B, new BigDecimal("00.91")));
            param.addShare(new Share(owner_C, new BigDecimal("71.14")));
            
            AmountPie result = sut.minus(param);
            
            assertThat(result.getShare(owner_A).getValue(), is(new BigDecimal("85.05")));
            assertThat(result.getShare(owner_B).getValue(), is(new BigDecimal("31.00")));
            assertThat(result.getShare(owner_C).getValue(), is(new BigDecimal("3.86")));
        }
        
        @Test
        public void parameterに存在しないshareの値には影響を与えない() {
            AmountPie param = new AmountPie();
            param.addShare(new Share(owner_B, new BigDecimal("00.91")));
            param.addShare(new Share(owner_C, new BigDecimal("71.14")));
            
            AmountPie result = sut.minus(param);
            
            assertThat("this value must not be change.", result.getShare(owner_A).getValue(), is(new BigDecimal("98.09")));
            assertThat(result.getShare(owner_B).getValue(), is(new BigDecimal("31.00")));
            assertThat(result.getShare(owner_C).getValue(), is(new BigDecimal("3.86")));
        }
    }
}
