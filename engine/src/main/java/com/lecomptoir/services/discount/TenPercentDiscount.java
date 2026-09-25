package com.lecomptoir.services.discount;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.lecomptoir.services.Cart;

public class TenPercentDiscount {
    private static final BigDecimal FIFTY = new BigDecimal("50.00");
    private static final BigDecimal RATE = new BigDecimal("0.10");

    public BigDecimal calculate(Cart cart) {
        BigDecimal total = cart.getTotal();

        if (total.compareTo(FIFTY) > 0) {
            return total.multiply(RATE).setScale(2, RoundingMode.HALF_UP);
        }
        return BigDecimal.ZERO;
    }
}
