package adapter;

public class ExternalPayPalService {
 
    public void makePayment(String currency, double amount) {
        System.out.printf("[PayPal] Pago realizado con PayPal: %2f.%n", currency, amount);
    }

}