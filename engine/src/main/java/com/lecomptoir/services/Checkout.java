package com.lecomptoir.services;




import java.math.BigDecimal;

import com.lecomptoir.services.discount.FiftyPercent;






public class Checkout {
    public String generateReceipt(Cart cart) {
        String receipt = "==== TICKET DE CAISSE ==== \n\n";

        for (CartLine line : cart.getAllLines()) {

            BigDecimal lineTotal = line.product().unitPrice().multiply(BigDecimal.valueOf(line.quantity()));

            receipt += "- "+ line.product().label()+" x"+line.quantity()+" : "+ lineTotal  + " EUR \n\n";
        }


        receipt += "-------------------------\n";




        BigDecimal gtotal = cart.getTotal();
        FiftyPercent discounted = new FiftyPercent();
        BigDecimal discount = discounted.calculate(cart);

        BigDecimal finalTotal = gtotal;




        if (discount.compareTo(BigDecimal.ZERO) > 0) {
            finalTotal = gtotal.subtract(discount);

            receipt += "TOTAL AVANT REMISE    : " + gtotal + " EUR \n\n";
            receipt += "REMISE DE 10% APPLICQUÉE: " + discount + " EUR \n\n";
            receipt += "-------------------------\n\n";
            receipt += "TOTAL NET A PAYER     : "+ finalTotal + " EUR \n";
        }
        else {
            receipt += "TOTAL A PAYER         : " + gtotal + " EUR \n";
        }


        receipt += "=========================\n";

        return receipt;
    }
}
