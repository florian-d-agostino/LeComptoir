package com.lecomptoir.services.discount;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.lecomptoir.services.CartLine;
import com.lecomptoir.services.enums.Category;
import com.lecomptoir.services.Cart;
import com.lecomptoir.services.Product;

public class TvaCalculator {

    public record TvaData(
            BigDecimal totalHT,
            BigDecimal tvaFood,
            BigDecimal tvaOther,
            BigDecimal totalTtc) {
    }

    public TvaData tvaCalculator(Cart cart) {
        List<CartLine> lines = new ArrayList<>();
        BigDecimal totalHT = BigDecimal.ZERO;
        BigDecimal tvaFood = BigDecimal.ZERO;
        BigDecimal tvaOther = BigDecimal.ZERO;
        BigDecimal totalTtc = BigDecimal.ZERO;
        Product product;

        lines = cart.getAllLines();

        for (CartLine cartLine : lines) {
            product = cartLine.product();

            for (int i = cartLine.quantity(); i > 0; i--) {

                if (product.category() == Category.OTHER) {

                    tvaOther = tvaOther.add(product.unitPrice().multiply(BigDecimal.valueOf(0.20)));

                } else {

                    tvaFood = tvaFood.add(product.unitPrice().multiply(BigDecimal.valueOf(0.055)));
                }

                totalHT = totalHT.add(product.unitPrice());
            }
        }

        totalTtc = totalTtc.add(tvaFood).add(tvaOther).add(totalHT);

        return new TvaData(totalHT, tvaFood, tvaOther, totalTtc);
    }
}
