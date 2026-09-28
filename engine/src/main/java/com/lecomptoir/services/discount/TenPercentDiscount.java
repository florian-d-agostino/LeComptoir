package com.lecomptoir.services.discount;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.lecomptoir.services.Cart;

public class TenPercentDiscount {
    private static final BigDecimal FIFTY = new BigDecimal("50.00");
    private static final BigDecimal RATE_FIFTY = new BigDecimal("0.10");
    private static final BigDecimal SEVENTY = new BigDecimal("70.00");
    private static final BigDecimal RATE_SEVENTY = new BigDecimal("0.15");

    public BigDecimal calculate(Cart cart) {
        BigDecimal total = cart.getTotal();

        if (total.compareTo(SEVENTY) > 0) {
            return total.multiply(RATE_SEVENTY).setScale(2, RoundingMode.HALF_UP);
        }
        if (total.compareTo(FIFTY) > 0) {
            return total.multiply(RATE_FIFTY).setScale(2, RoundingMode.HALF_UP);
        }
        return BigDecimal.ZERO;
    }
}
