package com.lecomptoir.services;

import java.math.BigDecimal;

import com.lecomptoir.services.discount.DrinkDiscount;
import com.lecomptoir.services.discount.FiftyPercent;
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






        // Drinks Discount (tag v3)
        DrinkDiscount drinkDisc = new DrinkDiscount();
        BigDecimal drinkDiscountAmount = drinkDisc.drinkDiscount(cart).abs();





        // 10% if > 50 EUR (tag v2)
        FiftyPercent discounted = new FiftyPercent();
        BigDecimal fiftyDiscount = discounted.calculate(cart);



        // Loyalty Discount (tag v5)
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
        receipt += "TOTAL HORS TAXES      : " + tva.totalHT() + " EUR \n";
        receipt += "TVA 5,5%              : " + tva.tvaFood() + " EUR \n";
        receipt += "TVA 20%               : " + tva.tvaOther() + " EUR \n";
        receipt += "TOTAL TTC             : " + tva.totalTtc() + " EUR \n";

        receipt += "=========================\n";


        // Update card
        if (card != null) {
            boolean usedLoyalty = discountLabel.equals("REMISE FIDELITE");
            card.usedPoints(usedLoyalty, finalTotal);
            receipt += "POINTS FIDELITE RESTANT       :" + card.getPoints() + " pts \n";
        }

        receipt += "=========================\n";

        return receipt;
    }
}
