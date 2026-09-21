package com.lecomptoir.services;

import java.math.BigDecimal;

import com.lecomptoir.services.discount.DrinkDiscount;
import com.lecomptoir.services.discount.FiftyPercent;

import com.lecomptoir.services.discount.TvaCalculator;
import com.lecomptoir.services.discount.TvaCalculator.TvaData;

public class Checkout {
    public String generateReceipt(Cart cart) {
        String receipt = "==== TICKET DE CAISSE ==== \n\n";

        for (CartLine line : cart.getAllLines()) {

            BigDecimal lineTotal = line.product().unitPrice().multiply(BigDecimal.valueOf(line.quantity()));

            receipt += "- " + line.product().label() + " x" + line.quantity() + " : " + lineTotal + " EUR \n\n";
        }

        receipt += "-------------------------\n";

        BigDecimal gtotal = cart.getTotal();
        BigDecimal finalTotal = gtotal;

        // 1. Drinks Discount (tag v3)
        DrinkDiscount drinkDisc = new DrinkDiscount();
        BigDecimal drinkDiscountAmount = drinkDisc.drinkDiscount(cart);
        boolean hasDrinkDiscount = drinkDiscountAmount.compareTo(BigDecimal.ZERO) < 0;

        // 2. 10% Discount if total > 50€ (tag v2)
        FiftyPercent discounted = new FiftyPercent();
        BigDecimal fiftyDiscount = discounted.calculate(cart);
        boolean hasFiftyDiscount = fiftyDiscount.compareTo(BigDecimal.ZERO) > 0;

        // Display discounts if at least one applies
        if (hasDrinkDiscount || hasFiftyDiscount) {
            receipt += "TOTAL AVANT REMISE    : " + gtotal + " EUR \n\n";

            if (hasDrinkDiscount) {
                finalTotal = finalTotal.add(drinkDiscountAmount);
                receipt += "OFFRE BOISSONS (3 POUR 2): " + drinkDiscountAmount + " EUR \n\n";
            }

            if (hasFiftyDiscount) {
                finalTotal = finalTotal.subtract(fiftyDiscount);
                receipt += "REMISE DE 10% APPLIQUÉE: -" + fiftyDiscount + " EUR \n\n";
            }

            receipt += "-------------------------\n\n";
            receipt += "TOTAL NET A PAYER     : " + finalTotal + " EUR \n";
        } else {
            receipt += "TOTAL A PAYER         : " + gtotal + " EUR \n";
        }

        // TvaCalculator (tag v4)
        TvaCalculator tvaCalc = new TvaCalculator();
        TvaData tva = tvaCalc.tvaCalculator(cart);

        receipt += "-------------------------\n";
        receipt += "TOTAL HORS TAXES      : " + tva.totalHT() + " EUR \n";
        receipt += "TVA 5,5%              : " + tva.tvaFood() + " EUR \n";
        receipt += "TVA 20%               : " + tva.tvaOther() + " EUR \n";
        receipt += "TOTAL TTC             : " + tva.totalTtc() + " EUR \n";

        receipt += "=========================\n";

        return receipt;
    }
}
