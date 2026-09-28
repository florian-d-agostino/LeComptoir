package com.lecomptoir.services;



import java.math.BigDecimal;
import java.math.RoundingMode;

import com.lecomptoir.services.discount.DiscountStrategy;
import com.lecomptoir.services.discount.DiscountStrategy.BestDiscountResult;
import com.lecomptoir.services.discount.LoyaltyCard;
import com.lecomptoir.services.discount.TvaCalculator;
import com.lecomptoir.services.discount.TvaCalculator.TvaData;




public class Checkout {
    public String generateReceipt(Cart cart, LoyaltyCard card) {
        String receipt = "~~~~==== TICKET DE CAISSE ====~~~~ \n\n";

        for (CartLine line : cart.getAllLines()) {

            BigDecimal lineTotal = line.product().unitPrice().multiply(BigDecimal.valueOf(line.quantity()));



            // Quantity
            receipt += "- " + line.product().label() + " x" + line.quantity() + " : " + lineTotal + " EUR \n\n";
        }

        receipt += "-------------------------\n";

        BigDecimal gtotal = cart.getTotal();
        BigDecimal finalTotal = gtotal;




        // Best Discount
        BestDiscountResult best = DiscountStrategy.findBestDiscount(cart, card);
        BigDecimal bestDiscount = best.amount();
        String discountLabel = best.label();



        // Display discounts
        if (bestDiscount.compareTo(BigDecimal.ZERO) > 0) {

            receipt += "TOTAL AVANT REMISE    : " + gtotal + " EUR \n\n";
            finalTotal = finalTotal.subtract(bestDiscount);

            receipt += discountLabel + ": -" + bestDiscount + " EUR \n\n";
            receipt += "-------------------------\n\n";

            receipt += "TOTAL NET A PAYER     : " + finalTotal + " EUR \n\n";
        } else {
            receipt += "TOTAL A PAYER         : " + gtotal + " EUR \n\n";
        }



        // TVA (tag v4)
        TvaCalculator tvaCalc = new TvaCalculator();
        TvaData tva = tvaCalc.tvaCalculator(cart);

        receipt += "-------------------------\n";
        receipt += "TOTAL HORS TAXES      : " + tva.totalHT().setScale(2, RoundingMode.HALF_UP) + " EUR \n";
        receipt += "TVA 5,5%              : " + tva.tvaFood().setScale(2, RoundingMode.HALF_UP) + " EUR \n";
        receipt += "TVA 20%               : " + tva.tvaOther().setScale(2, RoundingMode.HALF_UP) + " EUR \n";
        receipt += "-------------------------\n";
        receipt += "TOTAL TTC             : " + tva.totalTtc().setScale(2, RoundingMode.HALF_UP) + " EUR \n";

        receipt += "=========================\n";


        // Update card
        if (card != null) {
            boolean usedLoyalty = discountLabel.equals("REMISE FIDELITE");
            card.usedPoints(usedLoyalty, finalTotal);
            receipt += "POINTS FIDELITE RESTANT       : " + card.getPoints() + " pts \n";
        }

        receipt += "=========================\n";

        return receipt;
    }
}
