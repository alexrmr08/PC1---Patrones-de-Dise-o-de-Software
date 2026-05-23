package strategy;

public class NoDiscountStrategy implements DiscountStrategy {
    
    @Override
    public double applyDiscount(double total) {
        System.out.println(" [Descuento] Sin descuento aplicado.");
        return total;
    }

}