package service;

import adapter.PaymentProcessor;
import model.Cart;
import observer.OrderObserver;

import java.util.ArrayList;
import java.util.List;

public class OrderService {
    
    private Cart cart; 
    private PaymentProcessor paymentProcessor;
    private List<OrderObserver> observers; 

    public OrderService(Cart cart, PaymentProcessor paymentProcessor) {
        this.cart = cart;
        this.paymentProcessor = paymentProcessor;
        this.observers = new ArrayList<>();
    }

    public void addObserver(OrderObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(OrderObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String message) {
        for (OrderObserver observer: observers) {
            observer.update(message);
        }
    }

    public void confirmOrder() {
        double total = cart.calculateTotal();

        System.out.printf("%n --> Compra confirmada por S/. %.2f%n", total);

        System.out.println("--> Procesando pago...");
        paymentProcessor.pay(total);

        System.out.println("--> Notificando observadores...");
        String message = String.format("Compra realizada por S/. %.2f", total);
        notifyObservers(message);
    }
}
