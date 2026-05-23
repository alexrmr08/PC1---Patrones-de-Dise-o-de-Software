import adapter.*;
import model.Cart;
import model.Product;
import observer.*;
import service.OrderService;
import strategy.*;

public class Main {
 
    public static void main(String[] args) {

        //Introduccion a la tienda virtual 
        System.out.println("-----------------------------------------------------------");
        System.out.println("                     TIENDA VIRTUAL                        ");
        System.out.println("-----------------------------------------------------------");

        //1. Crear productos disponibles en el sistema
        System.out.println("\n[1] Productos disponibles: ");
        Product laptop = new Product("Macbook Pro Chip M5 Pro", 8000.0);
        Product mouse = new Product("Magic Mouse", 630.0);
        Product teclado = new Product("Magic Keyboard", 1300.0);
        System.out.println("    " + laptop);
        System.out.println("    " + mouse);
        System.out.println("    " + teclado);

        //2. Agregar productos al carrito
        System.out.println("\n[2] Agregar productos al carrito: ");
        Cart cart = new Cart();
        cart.addProduct(laptop);
        cart.addProduct(mouse);
        cart.addProduct(teclado);
        System.out.printf("Subtotal sin descuento: $%.2f%n", cart.calculateSubtotal());

        //3. Patron strategy 
        System.out.println("\n[3] Aplicando estrategia de descuento (Strategy):");
 
        // Ejemplo con descuento porcentual del 10%
        cart.setDiscountStrategy(new PercentageDiscountStrategy(10));
        double totalConDescuento = cart.calculateTotal();
        System.out.printf("  Total con descuento porcentual: S/ %.2f%n", totalConDescuento);

        //4. Patron Adapter
        System.out.println("\n[4] Configurando método de pago (Adapter):");
 
        ExternalPayPalService paypalExterno = new ExternalPayPalService();
        PaymentProcessor metodoPago = new PayPalAdapter(paypalExterno);
        System.out.println("  Método seleccionado: PayPal (via Adapter)");
 
        //5. Configurar el servicio de orden
        OrderService orderService = new OrderService(cart, metodoPago);

        //6. Patron Observer
        System.out.println("\n[5] Registrando observadores (Observer):");
        orderService.addObserver(new EmailNotificationObserver());
        orderService.addObserver(new InventoryObserver());
        orderService.addObserver(new AdminNotificationObserver());
        System.out.println("  Observadores registrados: Email, Inventario, Admin");

        //7. Confirmar la compra - dispara pago y notificaciones
        System.out.println("\n[6] Confirmando la compra:");
        orderService.confirmOrder();
 
        System.out.println("\n=================================================");
        System.out.println("           FLUJO COMPLETADO CON ÉXITO            ");
        System.out.println("=================================================");

        
    }
}