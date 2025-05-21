package autonoma.AtrapaComida.main;

import autonoma.AtrapaComida.ui.JuegoPanel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Clase principal que inicia la aplicación del juego "Atrapa Comida".
 * Esta clase crea y configura la ventana principal del juego.
 * 
 * @author Luisa Fernanda Henao Posada
 * @since 19-05-2025
 *  @version 1.0
 */
public class Main {
    /**
     * Método principal que inicia la aplicación.
     * Configura y muestra la ventana principal del juego.
     * 
     * @param args Argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Configuración de la ventana principal
            JFrame frame = new JFrame("Atrapa Comida");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(533, 398);  
            frame.setResizable(false); 
            frame.setLocationRelativeTo(null); 
            frame.setIconImage(new ImageIcon(Main.class.getResource("/autonoma/AtrapaComida/images/Icono.png")).getImage());

            // Creación y adición del panel del juego
            JuegoPanel panel = new JuegoPanel();
            frame.add(panel);

            // Hacer visible la ventana
            frame.setVisible(true);
        });
    }
}