package model;

import java.util.ArrayList;
import java.util.List;

import strategy.DiscountStrategy;
import strategy.NoDiscountStrategy;

public class Cart {
    
    private List<Product> products;
    private DiscountStrategy discountStrategy;

    public Cart() {
        this.products = new ArrayList<>();
        this.discountStrategy = new NoDiscountStrategy();
    }

    public void addProduct(Product product) {
        products.add(product);
        System.out.println("--> Producto agregado: " + product);
    }

    public double calculateSubtotal() {
        double subtotal = 0;
        for (Product product : products) {
            subtotal += product.getPrice();
        }
        return subtotal;
    }

    public double calculateTotal() {
        double subtotal = calculateSubtotal();
        return discountStrategy.applyDiscount(subtotal);
    }

    public void setDiscountStrategy(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    public List<Product> getProducts() {
        return products;
    }

}