package com.lecomptoir.services;

import java.math.BigDecimal;

public class Checkout {
    public String generateReceipt(Cart cart) {
        String receipt = "==== TICKET DE CAISSE ==== \n\n";

        for (CartLine line : cart.getAllLines()) {

            BigDecimal lineTotal = line.product().unitPrice().multiply(BigDecimal.valueOf(line.quantity()));

            receipt += "- "+ line.product().label()+" x"+line.quantity()+" : "+ lineTotal  + " EUR \n\n";
        }


        receipt += "-------------------------\n";
        receipt += "TOTAL A PAYER : "+ cart.getTotal() + " EUR\n";
        receipt += "=========================\n";

        return receipt;
    }
}
