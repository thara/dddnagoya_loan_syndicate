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
public class LoanSyndicateTest {

    @Test(expected = IllegalArgumentException.class)
    public void facilityに含まれる出資の割合の合計は100を超えてはならない() {
        
        Facility sut = new Facility(new BigDecimal(Integer.MAX_VALUE));
        
        sut.join(new Investment(new Company(1), new BigDecimal("50")));
        sut.join(new Investment(new Company(2), new BigDecimal("40")));
        
        // it should be thrown an error
        sut.join(new Investment(new Company(3), new BigDecimal("11")));
    }

    public static class LoanInvestmentTest {
//    public static class LoanInvestmentはFacilityにおける出資者の分配率に比例する {

        static final BigDecimal limit = new BigDecimal("1000");
        
        final Facility facility;
        
        final Company owner_A_30 = new Company(1);
        final Company owner_B_15 = new Company(2);
        final Company owner_C_45 = new Company(3);
        
        public LoanInvestmentTest() {
            facility = new Facility(limit);
            facility.join(new Investment(owner_A_30, new BigDecimal("30")));
            facility.join(new Investment(owner_B_15, new BigDecimal("15")));
            facility.join(new Investment(owner_C_45, new BigDecimal("45")));
        }
        
        @Test
        public void sumの値はincreaseとdecreaseで渡した値で加減算される() {
            Loan sut = new Loan(facility);
            
            sut.increase(new LoanInvestment(new BigDecimal("100")));
            assertThat(sut.getSum(), is(new BigDecimal("100")));
            
            sut.increase(new LoanInvestment(new BigDecimal("60")));
            assertThat(sut.getSum(), is(new BigDecimal("160")));
            
            sut.decrease(new LoanInvestment(new BigDecimal("90")));
            assertThat(sut.getSum(), is(new BigDecimal("70")));
            
            sut.increase(new LoanInvestment(new BigDecimal("20")));
            assertThat(sut.getSum(), is(new BigDecimal("90")));
            
            sut.decrease(new LoanInvestment(new BigDecimal("5")));
            assertThat(sut.getSum(), is(new BigDecimal("85")));
        }
        
        @Test
        public void increaseすると各LoanInvestmentが分配率によって増加する() {
            Loan sut = new Loan(facility);
            
            sut.increase(new LoanInvestment(new BigDecimal("100")));
            assertThat(sut.getLoanInvestment(owner_A_30).getAmount(), is(new BigDecimal("30.00")));
            assertThat(sut.getLoanInvestment(owner_B_15).getAmount(), is(new BigDecimal("15.00")));
            assertThat(sut.getLoanInvestment(owner_C_45).getAmount(), is(new BigDecimal("45.00")));
            
            sut.increase(new LoanInvestment(new BigDecimal("400")));
            assertThat(sut.getLoanInvestment(owner_A_30).getAmount(), is(new BigDecimal("150.00")));
            assertThat(sut.getLoanInvestment(owner_B_15).getAmount(), is(new BigDecimal("75.00")));
            assertThat(sut.getLoanInvestment(owner_C_45).getAmount(), is(new BigDecimal("225.00")));
            assertThat(sut.getSum(), is(new BigDecimal("500")));
        }
        
        @Test
        public void decreaseすると各LoanInvestmentが分配率によって減算する() {
            Loan sut = new Loan(facility);
            
            sut.increase(new LoanInvestment(new BigDecimal("800")));
            
            sut.decrease(new LoanInvestment(new BigDecimal("300")));
            assertThat(sut.getLoanInvestment(owner_A_30).getAmount(), is(new BigDecimal("150.00")));
            assertThat(sut.getLoanInvestment(owner_B_15).getAmount(), is(new BigDecimal("75.00")));
            assertThat(sut.getLoanInvestment(owner_C_45).getAmount(), is(new BigDecimal("225.00")));
            assertThat(sut.getSum(), is(new BigDecimal("500")));
            
            sut.decrease(new LoanInvestment(new BigDecimal("100")));
            assertThat(sut.getLoanInvestment(owner_A_30).getAmount(), is(new BigDecimal("120.00")));
            assertThat(sut.getLoanInvestment(owner_B_15).getAmount(), is(new BigDecimal("60.00")));
            assertThat(sut.getLoanInvestment(owner_C_45).getAmount(), is(new BigDecimal("180.00")));
            assertThat(sut.getSum(), is(new BigDecimal("400")));
        }
    }
}
