
package autonoma.AtrapaComida.elements;

import java.awt.Graphics;

/**
 * Clase abstracta que representa un elemento básico del juego ComidaQueCae.
 * Proporciona la estructura común para todos los elementos que aparecen en el juego,
 * incluyendo posición, dimensiones y comportamiento básico como caída y detección de colisiones.
 * 
 * @author Gilary 
 * @since 18-05-2025
 * @version 1.0
 */
public abstract class Elemento {
    /** Coordenada x (horizontal) del elemento */
    protected int x;
    
    /** Coordenada y (vertical) del elemento */
    protected int y;
    
    /** Ancho constante para todos los elementos */
    public static final int ANCHO = 50;
    
    /** Alto constante para todos los elementos */
    protected static final int ALTO = 50;

    /**
     * Constructor que inicializa un elemento en la posición horizontal especificada.
     * La posición vertical inicial siempre es 0 (parte superior de la pantalla).
     * 
     * @param x La posición horizontal inicial del elemento
     */
    public Elemento(int x) {
        this.x = x;
        this.y = 0; // empieza arriba
    }

    /**
     * Obtiene la coordenada x del elemento.
     * 
     * @return La posición horizontal actual del elemento
     */
    public int getX() {
        return x;
    }

    /**
     * Obtiene la coordenada y del elemento.
     * 
     * @return La posición vertical actual del elemento
     */
    public int getY() {
        return y;
    }

    /**
     * Método abstracto para dibujar el elemento en un contexto gráfico.
     * Debe ser implementado por las clases concretas que hereden de Elemento.
     * 
     * @param g El contexto gráfico donde se dibujará el elemento
     */
    public abstract void dibujar(Graphics g);

    /**
     * Verifica si un punto (px, py) está contenido dentro de los límites del elemento.
     * 
     * @param px Coordenada x del punto a verificar
     * @param py Coordenada y del punto a verificar
     * @return true si el punto está dentro del elemento, false en caso contrario
     */
    public boolean contiene(int px, int py) {
        return px >= x && px <= x + ANCHO && py >= y && py <= y + ALTO;
    }

    /**
     * Hace caer el elemento hacia abajo con la velocidad especificada.
     * Incrementa la coordenada y según la velocidad de caída.
     * 
     * @param velocidad Cantidad de píxeles que el elemento caerá en este movimiento
     */
    public void caer(int velocidad) {
        y += velocidad;
    }
}