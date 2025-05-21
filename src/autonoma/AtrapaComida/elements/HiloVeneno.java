package autonoma.AtrapaComida.elements;

import autonoma.AtrapaComida.ui.JuegoPanel;
import java.util.List;
import java.util.Random;

/**
 * Hilo encargado de controlar la generación y movimiento de elementos de veneno en el juego.
 * Gestiona la aparición aleatoria de veneno, su caída y eliminación cuando salen de la pantalla.
 * Se detiene cuando la partida termina (por ejemplo, si una comida no es atrapada).
 * 
 * @author Gilary
 * @since 18-05-2025
 * @version 1.1
 */
public class HiloVeneno implements Runnable {
    /** Lista que contiene todos los elementos de veneno activos en el juego */
    private final List<Veneno> venenos;

    /** Panel principal del juego donde se renderizan los elementos */
    private final JuegoPanel panel;

    /** Generador de números aleatorios para posicionamiento */
    private final Random random = new Random();

    /** Bandera para controlar la ejecución del hilo */
    private volatile boolean activo = true;

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
     * Detiene la ejecución del hilo.
     */
    public void detener() {
        activo = false;
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
        while (activo) {
            synchronized (venenos) {
                // Generación de nuevo veneno si no se ha alcanzado el límite
                if (venenos.size() < 6) {
                    int maxX = panel.getWidth() - Veneno.ANCHO;

                    if (maxX <= 0) {
                        dormir(50);
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

                    venenos.add(new Veneno(xNuevo));
                }

                // Mover venenos hacia abajo y eliminarlos si salen de la pantalla
                for (int i = 0; i < venenos.size(); i++) {
                    Veneno v = venenos.get(i);
                    v.caer(3);

                    if (v.getY() > panel.getHeight()) {
                        venenos.remove(i);
                        i--;
                    }
                }
            }

            // Actualizar vista
            panel.repaint();
            dormir(50);
        }
    }

    /**
     * Método auxiliar para pausar el hilo unos milisegundos.
     * 
     * @param ms Tiempo en milisegundos para dormir
     */
    private void dormir(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
        }
    }
}
