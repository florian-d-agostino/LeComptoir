package com.lecomptoir.services.discount;



import java.math.BigDecimal;


public class LoyaltyCard {
    private int points;


    public LoyaltyCard(int initialPoints) {
        this.points = initialPoints;
    }

    public int getPoints() {
        return points;
    }



    // Calculate discount
    public BigDecimal calculateDiscount() {
        int slices = points / 100;
        return BigDecimal.valueOf(slices * 5).setScale(2);

    }


    // Points used
    public int getPointsUsed() {
        return (points / 100) * 100;
    }


    // - Points
    public void usedPoints(boolean usedDiscount, BigDecimal spent) {
        if (usedDiscount) {
            points -= getPointsUsed();
        }


        // + Points
        int added = spent.intValue();
        points += added;
    }
}
