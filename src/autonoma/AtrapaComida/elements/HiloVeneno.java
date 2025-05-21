
package autonoma.AtrapaComida.elements;


import autonoma.AtrapaComida.ui.JuegoPanel;
import java.util.List;
import java.util.Random;
import javax.swing.Timer;

/**
 * Hilo encargado de controlar la generación y movimiento de elementos de veneno en el juego.
 * Gestiona la aparición aleatoria de veneno, su caída y eliminación cuando salen de la pantalla.
 * 
 * @author Gilary
 * @since 18-05-2025
 * @version 1.0
 */
public class HiloVeneno implements Runnable {
    /** Lista que contiene todos los elementos de veneno activos en el juego */
    private final List<Veneno> venenos;
    
    /** Panel principal del juego donde se renderizan los elementos */
    private final JuegoPanel panel;
    
    /** Generador de números aleatorios para posicionamiento */
    private final Random random = new Random();
    
    /** Temporizador no utilizado actualmente (reservado para futuras implementaciones) */
    private Timer timer;

    /**
     * Construye un nuevo hilo de control para elementos de veneno.
     * 
     * @param venenos Lista compartida de elementos de veneno activos
     * @param panel Referencia al panel principal del juego para actualización visual
     */
    public HiloVeneno(List<Veneno> venenos, JuegoPanel panel) {
        this.venenos = venenos;
        this.panel = panel;
    }

    /**
     * Verifica si una nueva instancia de veneno en la posición especificada
     * se superpondría con elementos existentes.
     * 
     * @param xNuevo Posición horizontal propuesta para el nuevo veneno
     * @return true si existe superposición, false en caso contrario
     */
    private boolean seSuperpone(int xNuevo) {
        for (Veneno v : venenos) {
            if (Math.abs(v.getX() - xNuevo) < Veneno.ANCHO) {
                return true;
            }
        }
        return false;
    }

    /**
     * Método principal del hilo que controla el ciclo de vida del veneno.
     * Se ejecuta continuamente mientras el juego esté activo, gestionando:
     * - Generación de nuevos elementos
     * - Movimiento hacia abajo
     * - Eliminación al salir de la pantalla
     * - Actualización visual
     */
    @Override
    public void run() {
        while (true) {
            synchronized (venenos) {
                // Generación de nuevo veneno si no se ha alcanzado el límite
                if (venenos.size() < 6) {
                    int maxX = panel.getWidth() - Veneno.ANCHO;
                    
                    // Esperar si el panel no tiene ancho válido aún
                    if (maxX <= 0) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ignored) {}
                        continue;
                    }
                    
                    // Buscar posición no superpuesta
                    int xNuevo;
                    do {
                        xNuevo = random.nextInt(maxX);
                    } while (seSuperpone(xNuevo));
                    
                    venenos.add(new Veneno(xNuevo));
                }

                // Mover y verificar todos los elementos de veneno
                for (int i = 0; i < venenos.size(); i++) {
                    Veneno v = venenos.get(i);
                    v.caer(5); // Mover hacia abajo
                    
                    // Eliminar si sale por la parte inferior
                    if (v.getY() > panel.getHeight()) {
                        venenos.remove(i);
                        i--; // Ajustar índice después de remover
                    }
                }
            }
            
            // Actualizar visualización
            panel.repaint();
            
            // Pausa para controlar velocidad del juego
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}