package org.dddnagoya.loan_syndicate;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

/**
 * @author t_hara
 */
public class LoanSyndicateTest {
    
    static final Company owner_A = new Company(1);
    static final Company owner_B = new Company(2);
    static final Company owner_C = new Company(3);

    @Test
    public void ファシリティの分担率に基づいて引き出しが配分される() {
        
        Facility initialFacility = new Facility(new BigDecimal("30"));
        initialFacility.join(owner_A, new BigDecimal("2"));
        initialFacility.join(owner_B, new BigDecimal("7"));
        initialFacility.join(owner_C, new BigDecimal("1"));
        
        LoanSyndicate sut = new LoanSyndicate(initialFacility);
        
        sut.draw(new BigDecimal("20"));
        
        Loan loan = sut.getLastLoan();
        
        assertThat(loan.getAmount().setScale(0), is(new BigDecimal("20")));
        assertThat(loan.getAmountBy(owner_A), is(new BigDecimal("4")));
        assertThat(loan.getAmountBy(owner_B), is(new BigDecimal("14")));
        assertThat(loan.getAmountBy(owner_C), is(new BigDecimal("2")));
    }
    
    @Test
    public void 買い出し側の1社が2回目の資金引き出しに加わらない場合() {
        
        Facility initialFacility = new Facility(new BigDecimal("100"));
        initialFacility.join(owner_A, new BigDecimal("5"));
        initialFacility.join(owner_B, new BigDecimal("3"));
        initialFacility.join(owner_C, new BigDecimal("2"));
        
        LoanSyndicate sut = new LoanSyndicate(initialFacility);
        
        Loan loan = sut.draw(new BigDecimal("50"));
        
        // A:2, B:1.5, C:1.5
        assertThat(loan.getAmountBy(owner_A), is(new BigDecimal("25")));
        assertThat(loan.getAmountBy(owner_B), is(new BigDecimal("15")));
        assertThat(loan.getAmountBy(owner_C), is(new BigDecimal("10")));
        
        // B社が参加しないため、その分をA社が引き受ける
        Facility secondFacility = initialFacility.transfer(owner_B, owner_A, new BigDecimal("3"));
        
        assertThat(secondFacility.getPercentageOf(owner_A), is(new BigDecimal("8")));
        assertThat(secondFacility.getPercentageOf(owner_B), is(new BigDecimal("0")));
        assertThat(secondFacility.getPercentageOf(owner_C), is(new BigDecimal("2")));

        loan = sut.draw(new BigDecimal("10"), secondFacility);
        
        // 現在のローンの分配率はファシリティの分配率に比例していない
        assertThat(loan.getAmountBy(owner_A), is(new BigDecimal("33")));
        assertThat(loan.getAmountBy(owner_B), is(new BigDecimal("15")));
        assertThat(loan.getAmountBy(owner_C), is(new BigDecimal("12")));
        
        assertThat(loan.getAmount(), is(new BigDecimal("60")));
    }
    
    @Test
    public void 元本の支払いは常に未払いローンに対する分配率に比例して配分される() {
        
        Facility initialFacility = new Facility(new BigDecimal("100"));
        initialFacility.join(owner_A, new BigDecimal("6"));
        initialFacility.join(owner_C, new BigDecimal("4"));
        
        LoanSyndicate sut = new LoanSyndicate(initialFacility);

        // ローンの配分率: A:6, B:4
        Loan loan = sut.draw(new BigDecimal("20"));
        assertThat(loan.getAmountBy(owner_A), is(new BigDecimal("12")));
        assertThat(loan.getAmountBy(owner_C), is(new BigDecimal("8")));
        
        Facility secondFacility = initialFacility.transfer(owner_C, owner_A, new BigDecimal("2"));
        
        assertThat(secondFacility.getPercentageOf(owner_A), is(new BigDecimal("8")));
        assertThat(secondFacility.getPercentageOf(owner_C), is(new BigDecimal("2")));
        
        loan = sut.draw(new BigDecimal("30"), secondFacility);
        
        // ローンの配分率: A : B = 18 : 7
        assertThat(loan.getAmountBy(owner_A), is(new BigDecimal("36")));
        assertThat(loan.getAmountBy(owner_C), is(new BigDecimal("14")));
        
        assertThat(loan.getAmount(), is(new BigDecimal("50")));
        
        // 20 / (18 + 7) = 0.8
        loan = sut.payPrincipal(new BigDecimal("20"));
        assertThat(loan.getAmount(), is(new BigDecimal("30.0")));
        
        // A社に対する元本支払い: 0.8 * 18 = 14.4
        // A社のローンの残り   : 36 - 14.4 = 21.6
        assertThat(loan.getAmountBy(owner_A), is(new BigDecimal("21.6")));
        // B社に対する元本支払い: 0.8 + 7 = 5.6
        // B社のローンの残り   : 14 - 5.6 = 8.4
        assertThat(loan.getAmountBy(owner_C), is(new BigDecimal("8.4")));
    }
}
