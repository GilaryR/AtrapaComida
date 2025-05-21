package autonoma.AtrapaComida.elements;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * Representa un elemento de veneno en el juego ComidaQueCae.
 * Hereda de la clase Elemento y sobrescribe su comportamiento visual.
 * El veneno es un elemento que el jugador debe evitar recoger.
 * 
 * @author Gilary
 * @since 18-05-2025
 * @version 1.0
 */
public class Veneno extends Elemento {
    /** Imagen que representa visualmente el elemento veneno */
    private final Image imagen;

    /**
     * Construye un nuevo elemento de veneno en la posición horizontal especificada.
     * La posición vertical inicial será 0 (parte superior de la pantalla).
     * Carga la imagen del veneno desde los recursos de la aplicación.
     * 
     * @param x Posición horizontal inicial donde aparecerá el veneno
     */
    public Veneno(int x) {
        super(x);
        imagen = new ImageIcon(getClass().getResource("/autonoma/AtrapaComida/images/Veneno.png")).getImage();
    }

    /**
     * Dibuja el elemento veneno en el contexto gráfico proporcionado.
     * Utiliza la imagen cargada en el constructor para representar visualmente el veneno.
     * 
     * @param g El contexto gráfico donde se dibujará el elemento
     */
    @Override
    public void dibujar(Graphics g) {
        g.drawImage(imagen, x, y, ANCHO, ALTO, null);
    }
}