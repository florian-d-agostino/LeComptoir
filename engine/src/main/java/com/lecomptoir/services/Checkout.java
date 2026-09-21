package com.lecomptoir.services;

import java.math.BigDecimal;

public class Checkout {
    public String generateReceipt(Cart cart) {
        String receipt = "==== TICKET DE CAISSE ====\n";

        for (CartLine line : cart.lines()) {

            receipt += "- "+ line.product().label()+" x"+line.quantity()+" : "+ line.total() + "€\n";
        }

        receipt += "-------------------------\n";
        receipt += "TOTAL A PAYER : "+ cart.total() + "€\n";
        receipt += "=========================\n";

        return receipt;
    }
}
