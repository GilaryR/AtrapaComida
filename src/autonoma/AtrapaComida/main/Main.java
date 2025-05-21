package autonoma.AtrapaComida.main;

import autonoma.AtrapaComida.ui.JuegoPanel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Lluvia de Comida");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(533, 398);  
            frame.setResizable(false); 
            frame.setLocationRelativeTo(null); 
            frame.setIconImage(new ImageIcon(Main.class.getResource("/autonoma/ComidaQueCae/images/Icono.png")).getImage());

            JuegoPanel panel = new JuegoPanel();
            frame.add(panel);

            frame.setVisible(true);
        });
    }
}
