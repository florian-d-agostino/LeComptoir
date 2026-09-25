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
        // Filter drink items and sort them by unit price in ascending order
        List<CartLine> drinkLines = cart.getAllLines().stream()
                .filter(line -> line.product().category() == Category.DRINK)
                .sorted((l1, l2) -> l1.product().unitPrice().compareTo(l2.product().unitPrice()))
                .toList();

        // Calculate total number of drinks and number of free drinks (1 free for every 3 drinks)
        int totalDrinks = drinkLines.stream().mapToInt(CartLine::quantity).sum();
        int freeDrinksCount = totalDrinks / 3;

        if (freeDrinksCount == 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal totalDiscount = BigDecimal.ZERO;

        // Apply discount starting from the cheapest drinks
        for (CartLine line : drinkLines) {
            if (freeDrinksCount <= 0) {
                break;
            }

            // Determine how many free drinks can be consumed from this line
            int taken = Math.min(line.quantity(), freeDrinksCount);
            BigDecimal discountForLine = line.product().unitPrice().multiply(BigDecimal.valueOf(taken));
            totalDiscount = totalDiscount.add(discountForLine);
            freeDrinksCount -= taken;
        }

        // Return the discount as a negative amount
        return totalDiscount.negate();
    }
}
