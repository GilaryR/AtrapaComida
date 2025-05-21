
package autonoma.AtrapaComida.elements;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * Representa un elemento de comida en el juego ComidaQueCae.
 * La comida tiene una imagen asociada y puede dibujarse en un contexto gráfico.
 * 
 * @author Gilary
 * @since 18-05-2025
 * @version 1.0
 */
public class Comida extends Elemento {
    /** La imagen que representa visualmente la comida */
    private final Image imagen;

    /**
     * Crea una nueva instancia de Comida en la posición horizontal especificada.
     * 
     * @param x La posición horizontal inicial de la comida.
     */
    public Comida(int x) {
        super(x);
        imagen = new ImageIcon(getClass().getResource("/autonoma/AtrapaComida/images/Comida.png")).getImage();
    }

    /**
     * Dibuja la comida en el contexto gráfico especificado.
     * 
     * @param g El contexto gráfico donde se dibujará la comida.
     */
    @Override
    public void dibujar(Graphics g) {
        g.drawImage(imagen, x, y, ANCHO, ALTO, null);
    }
}