
package autonoma.AtrapaComida.elements;

import java.util.Random;
import javax.swing.JPanel;

/**
 * Clase responsable de generar elementos aleatorios (comida o veneno) para el juego ComidaQueCae.
 * Los elementos son generados en posiciones horizontales aleatorias dentro de los límites del panel.
 * 
 * @author Gilary
 * @since 18-05-2025
 * @version 1.0
 */
public class GeneradorElementos {
    /** Panel del juego donde aparecerán los elementos generados */
    private JPanel panel;
    
    /** Generador de números aleatorios para posiciones y tipos de elementos */
    private Random random = new Random();

    /**
     * Crea un nuevo generador de elementos asociado al panel especificado.
     * 
     * @param panel El panel del juego donde aparecerán los elementos generados
     */
    public GeneradorElementos(JPanel panel) {
        this.panel = panel;
    }

    /**
     * Genera un nuevo elemento de comida en una posición horizontal aleatoria.
     * 
     * @return Una nueva instancia de Comida en posición aleatoria
     */
    public Comida generarComida() {
        int x = random.nextInt(Math.max(panel.getWidth() - 20, 1));
        return new Comida(x);
    }

    /**
     * Genera un nuevo elemento de veneno en una posición horizontal aleatoria.
     * 
     * @return Una nueva instancia de Veneno en posición aleatoria
     */
    public Veneno generarVeneno() {
        int x = random.nextInt(Math.max(panel.getWidth() - 20, 1));
        return new Veneno(x);
    }

    /**
     * Genera un elemento aleatorio (comida o veneno) en posición horizontal aleatoria.
     * Tiene un 50% de probabilidad de generar cada tipo.
     * 
     * @return Un elemento aleatorio (Comida o Veneno) en posición aleatoria
     */
    public Elemento generarAleatorio() {
        if (random.nextBoolean()) {
            return generarComida();
        } else {
            return generarVeneno();
        }
    }
}
