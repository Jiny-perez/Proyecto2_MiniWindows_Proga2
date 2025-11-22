package Main;

import MiniWindows.PantallaLogin;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author marye
 */
public class Main {
    
    public static void main(String[] args) {
        try {
            
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("No se pudo establecer el Look and Feel del sistema");
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            PantallaLogin login = new PantallaLogin();
            login.setVisible(true);
        });
    }
}