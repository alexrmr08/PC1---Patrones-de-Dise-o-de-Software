package observer;

public class AdminNotificationObserver implements OrderObserver{
    
    @Override
    public void update(String message) {
        System.out.println("[Admin] Notificando al administrador: nueva orden confirmada.");
    }
    
}