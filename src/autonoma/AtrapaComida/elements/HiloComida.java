
package autonoma.AtrapaComida.elements;
import autonoma.AtrapaComida.ui.JuegoPanel;
import java.util.List;
import java.util.Random;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;

/**
 * Hilo encargado de controlar la generación y movimiento de los elementos de comida en el juego.
 * Maneja la lógica de caída de la comida, verifica colisiones y controla el flujo del juego.
 * 
 * @author Gilary
 * @since 18-05-2025
 * @version 1.0
 */
public class HiloComida implements Runnable {
    /** Lista que contiene todas las comidas activas en el juego */
    private final List<Comida> comidas;
    
    /** Panel principal del juego donde se renderizan los elementos */
    private final JuegoPanel panel;
    
    /** Generador de números aleatorios para posicionamiento */
    private final Random random = new Random();

    /**
     * Crea un nuevo hilo de comida con las referencias necesarias.
     * 
     * @param comidas Lista compartida de comidas activas
     * @param panel Referencia al panel principal del juego
     */
    public HiloComida(List<Comida> comidas, JuegoPanel panel) {
        this.comidas = comidas;
        this.panel = panel;
    }

    /**
     * Verifica si una nueva comida en la posición xNuevo se superpondría con comidas existentes.
     * 
     * @param xNuevo Posición horizontal propuesta para nueva comida
     * @return true si hay superposición, false en caso contrario
     */
    private boolean seSuperpone(int xNuevo) {
        for (Comida c : comidas) {
            if (Math.abs(c.getX() - xNuevo) < Comida.ANCHO) {
                return true;
            }
        }
        return false;
    }

    /**
     * Método principal del hilo que controla el ciclo de juego.
     * Se ejecuta continuamente mientras el juego esté activo.
     */
    @Override
    public void run() {
        while (true) {
            synchronized (comidas) {
                // Generación de nueva comida si hay espacio
                if (comidas.size() < 6) {
                    int maxX = panel.getWidth() - Comida.ANCHO;
                    if (maxX <= 0) {
                        dormir(100);
                        continue;
                    }

                    // Buscar posición no superpuesta
                    int xNuevo;
                    int intentos = 0;
                    do {
                        xNuevo = random.nextInt(maxX);
                        intentos++;
                        if (intentos > 10) break;
                    } while (seSuperpone(xNuevo));

                    comidas.add(new Comida(xNuevo));
                }

                // Movimiento y verificación de comidas
                for (int i = 0; i < comidas.size(); i++) {
                    Comida c = comidas.get(i);
                    c.caer(5);
                    
                    // Verificar si la comida salió de la pantalla
                    if (c.getY() > panel.getHeight()) {
                        SwingUtilities.invokeLater(() -> {
                            JOptionPane.showMessageDialog(panel, "¡Perdiste! La comida cayó sin ser atrapada.");
                            System.exit(0);
                        });
                        return;
                    }
                }
            }

            panel.repaint();
            dormir(50);
        }
    }

    /**
     * Detiene temporalmente la ejecución del hilo.
     * 
     * @param ms Milisegundos a dormir el hilo
     */
    private void dormir(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
            // No se requiere acción específica si el hilo es interrumpido
        }
    }
}