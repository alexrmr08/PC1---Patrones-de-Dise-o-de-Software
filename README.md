# Virtual Store 

Tienda virtual de consola que demuestra la aplicación de los patrones **Strategy**, **Adapter** y **Observer** en un flujo real de compra.

---

## Descripción del problema

Se necesita una tienda virtual que permita:
- Agregar productos a un carrito.
- Aplicar distintos tipos de descuento de forma intercambiable.
- Pagar con distintos métodos (incluyendo servicios externos con interfaces incompatibles).
- Notificar automáticamente a múltiples sistemas al confirmar una compra.

Cada uno de estos problemas se resuelve con un patrón de diseño distinto.

---

## Patrones implementados

### Strategy — Descuentos

**Problema:** la lógica de descuento varía según campañas o tipos de cliente, y no debe estar embebida en el carrito.

**Solución:** la interfaz `DiscountStrategy` define el contrato. El `Cart` recibe cualquier implementación concreta por composición y puede cambiarla en tiempo de ejecución sin modificar su código.

| Clase | Comportamiento |
|---|---|
| `NoDiscountStrategy` | No aplica descuento |
| `PercentageDiscountStrategy` | Descuento porcentual (ej. 10%) |
| `FixedAmountDiscountStrategy` | Descuento fijo (ej. S/ 50.00) |

### Adapter — Métodos de pago

**Problema:** `ExternalPayPalService` (librería de terceros) usa `makePayment(String, double)`, pero la tienda trabaja con la interfaz `PaymentProcessor` que define `pay(double)`. No se puede modificar el servicio externo.

**Solución:** `PayPalAdapter` implementa `PaymentProcessor` y traduce internamente la llamada a `makePayment`. La tienda siempre llama a `pay()` sin saber qué hay debajo.

Además, `CreditCardPaymentProcessor` y `YapePaymentProcessor` implementan `PaymentProcessor` directamente (sin adapter).

### Observer — Notificaciones de compra

**Problema:** al confirmar una orden, múltiples sistemas independientes deben reaccionar (correo, inventario, admin), pero `OrderService` no debe conocer ni depender de cada uno.

**Solución:** `OrderService` mantiene una lista de `OrderObserver`. Al confirmar, llama a `notifyObservers()` y cada observador actúa de forma autónoma.

| Observador | Acción |
|---|---|
| `EmailNotificationObserver` | Simula envío de correo al cliente |
| `InventoryObserver` | Actualiza el inventario |
| `AdminNotificationObserver` | Notifica al administrador |

---

## Estructura del proyecto

virtual-store/
├── src/
│   ├── Main.java
│   ├── model/
│   │   ├── Product.java
│   │   └── Cart.java
│   ├── strategy/
│   │   ├── DiscountStrategy.java
│   │   ├── NoDiscountStrategy.java
│   │   ├── PercentageDiscountStrategy.java
│   │   └── FixedAmountDiscountStrategy.java
│   ├── adapter/
│   │   ├── PaymentProcessor.java
│   │   ├── ExternalPayPalService.java
│   │   ├── PayPalAdapter.java
│   │   ├── CreditCardPaymentProcessor.java
│   │   └── YapePaymentProcessor.java
│   ├── observer/
│   │   ├── OrderObserver.java
│   │   ├── EmailNotificationObserver.java
│   │   ├── InventoryObserver.java
│   │   └── AdminNotificationObserver.java
│   └── service/
│       └── OrderService.java
└── README.md

---

## Cómo compilar y ejecutar

```bash
mkdir out
javac -d out src/model/*.java src/strategy/*.java src/adapter/*.java src/observer/*.java src/service/*.java src/Main.java
java -cp out Main
```

---

## Salida esperada en consola

=================================================
TIENDA VIRTUAL - DEMO DE PATRONES
[1] Productos disponibles:

Laptop HP 15 (S/ 1800.00)
Mouse Inalámbrico (S/ 45.00)
Teclado Mecánico (S/ 120.00)

[2] Agregando productos al carrito:
-> Producto agregado: Laptop HP 15 (S/ 1800.00)
-> Producto agregado: Mouse Inalámbrico (S/ 45.00)
-> Producto agregado: Teclado Mecánico (S/ 120.00)
Subtotal sin descuento: S/ 1965.00
[3] Aplicando estrategia de descuento (Strategy):
[Descuento] 10% de descuento aplicado: -S/ 196.50
Total con descuento porcentual: S/ 1768.50
[4] Configurando método de pago (Adapter):
Método seleccionado: PayPal (via Adapter)
[5] Registrando observadores (Observer):
Observadores registrados: Email, Inventario, Admin
[6] Confirmando la compra:



Compra confirmada por S/ 1768.50
Procesando pago...
[PayPal] Pago realizado con PayPal: PEN 1768.50
Notificando observadores...
[Email] Enviando correo al cliente: "Compra realizada por S/ 1768.50"
[Inventario] Actualizando inventario tras la compra...
[Admin] Notificando al administrador: nueva orden confirmada.



=================================================
FLUJO COMPLETADO CON ÉXITO
