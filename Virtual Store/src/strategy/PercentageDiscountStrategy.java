package strategy;

public class PercentageDiscountStrategy implements DiscountStrategy {
    
        private double percentage; 

        public PercentageDiscountStrategy(double percentage) {
            this.percentage = percentage;
        }

        @Override
        public double applyDiscount(double total) {
            double discount = total * (percentage / 100); 
            double finalTotal = total - discount; 
            System.out.printf(" [Descuento] %.0f%% de descuento aplicado: -S/. %.2f%n", percentage, discount);
            return finalTotal;
        }
}