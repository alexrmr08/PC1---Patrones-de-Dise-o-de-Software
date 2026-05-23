package adapter;

public class CreditCardPaymentProcessor implements PaymentProcessor {
 
    @Override
    public void pay(double amount) {
        System.out.printf("[Tarjeta de crédito] Cargo realizado por S/. %.2f%n", amount);
    }

}