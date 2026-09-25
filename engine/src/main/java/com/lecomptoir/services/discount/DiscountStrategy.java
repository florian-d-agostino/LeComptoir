package com.lecomptoir.services.discount;

import java.math.BigDecimal;

import com.lecomptoir.services.Cart;

public class DiscountStrategy {

    public record BestDiscountResult(BigDecimal amount, String label) {}

    public static BestDiscountResult findBestDiscount(Cart cart, LoyaltyCard card) {




        // Discount

        DrinkDiscount drinkDisc = new DrinkDiscount();
        BigDecimal drinkDiscountAmount = drinkDisc.drinkDiscount(cart).abs();

        TenPercentDiscount tenPercent = new TenPercentDiscount();
        BigDecimal fiftyDiscount = tenPercent.calculate(cart);

        BigDecimal loyaltyDiscount = (card != null) ? card.calculateDiscount() : BigDecimal.ZERO;




        // Best Discount

        BigDecimal bestDiscount = drinkDiscountAmount;
        String discountLabel = "OFFRE BOISSONS (3 POUR 2)";

        if (fiftyDiscount.compareTo(bestDiscount) > 0) {
            bestDiscount = fiftyDiscount;
            discountLabel = "REMISE DE 10% APPLIQUÉE";
        }

        if (loyaltyDiscount.compareTo(bestDiscount) > 0) {
            bestDiscount = loyaltyDiscount;
            discountLabel = "REMISE FIDELITE";
        }

        return new BestDiscountResult(bestDiscount, discountLabel);
    }
}