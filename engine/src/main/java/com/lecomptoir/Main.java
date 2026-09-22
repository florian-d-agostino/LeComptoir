package com.lecomptoir;
import com.lecomptoir.services.Cart;
import com.lecomptoir.services.CartLine;
import com.lecomptoir.services.Catalog;
import com.lecomptoir.services.Checkout;
import com.lecomptoir.services.discount.LoyaltyCard;
public class Main {


    public static void main(String[] args) {


        LoyaltyCard card = new LoyaltyCard(150);



        Cart cart = new Cart();
        cart.addLine(new CartLine(Catalog.BAGUETTE, 2));
        cart.addLine(new CartLine(Catalog.COCA, 3));
        cart.addLine(new CartLine(Catalog.STEAK, 1));
        cart.addLine(new CartLine(Catalog.BROOM, 1));



        Checkout checkout = new Checkout();
        String receipt = checkout.generateReceipt(cart, card);
        System.out.println(receipt);
    }
}