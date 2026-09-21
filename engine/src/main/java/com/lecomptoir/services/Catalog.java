package com.lecomptoir.services;

import java.math.BigDecimal;
import java.util.List;

import com.lecomptoir.services.enums.Category;



public class Catalog {
    public static final Product BAGUETTE = new Product("P1", "Baguette Tradition", new BigDecimal("1.20"), Category.BAKERY);
    public static final Product COCA     = new Product("P2", "Coca-Cola 33cl", new BigDecimal("2.50"), Category.DRINK);
    public static final Product STEAK    = new Product("P3", "Steak Haché", new BigDecimal("4.80"), Category.MEAT);
    public static final Product ICE_TEA  = new Product("P4", "Ice Tea Pêche", new BigDecimal("2.00"), Category.DRINK);
    public static final Product WATER    = new Product("P5", "Eau Minérale 1L", new BigDecimal("1.00"), Category.DRINK);
    public static final Product BROOM    = new Product("P6", "Balais ", new BigDecimal("47.00"), Category.OTHER);

    public static List<Product> getAll() {
        return List.of(BAGUETTE, COCA, STEAK, ICE_TEA, WATER);
    }
}
