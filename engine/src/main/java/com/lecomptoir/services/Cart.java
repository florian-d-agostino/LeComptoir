package com.lecomptoir.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartLine> lines = new ArrayList<>();

    public void addLine(CartLine line) {
        this.lines.add(line);
    }

    public List<CartLine> getAllLines() {
        return lines;
    }

    public BigDecimal getTotal() {
        BigDecimal total = BigDecimal.ZERO;

        for (CartLine line : lines) {
            BigDecimal unitPrice = line.product().unitPrice(); // Get Price
            BigDecimal lineTotal = unitPrice.multiply(BigDecimal.valueOf(line.quantity())); // Multiply by Quantity

            total = total.add(lineTotal);
        }

        return total;

    }
}
