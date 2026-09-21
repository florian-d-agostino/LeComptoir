package com.lecomptoir.services.discount;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.lecomptoir.services.Cart;
import com.lecomptoir.services.CartLine;
import com.lecomptoir.services.Product;
import com.lecomptoir.services.enums.Category;

/**
 * Return NEGATIVE total discount for each 3 drink 1 offer.
 */
public class DrinkDiscount {

    public BigDecimal drinkDiscount(Cart cart) {

        List<CartLine> lines = new ArrayList<>();
        List<BigDecimal> list = new ArrayList<>();
        Product product;

        lines = cart.getAllLines();

        // Get all DRINK products in list
        for (CartLine cartLine : lines) {
            product = cartLine.product();

            if (product.category() == Category.DRINK) {
                for (int i = cartLine.quantity(); i > 0; i--) {
                    list.add(product.unitPrice());
                }
            }
        }

        int discountNumber = list.size() / 3; // Init how much discount / 3 products

        // Check if more than 3 products
        if (list.size() >= 3) { // More than 3
            list.sort(null); // Sort list

            BigDecimal totalDiscount = BigDecimal.ZERO;

            for (int i = 0; i < discountNumber; i++) { // for discountNumber times
                totalDiscount = totalDiscount.add(list.get(i).negate()); // Add negative unitPrice to total discount
            }
            return totalDiscount;
        }

        else {
            return BigDecimal.ZERO; // return 0 if less than 3 products
        }
    }
}
