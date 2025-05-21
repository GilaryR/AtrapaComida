package autonoma.AtrapaComida.ui;

import autonoma.AtrapaComida.elements.Comida;
import autonoma.AtrapaComida.elements.HiloComida;
import autonoma.AtrapaComida.elements.HiloVeneno;
import autonoma.AtrapaComida.elements.Veneno;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Panel principal del juego "Atrapa Comida" que gestiona la lógica del juego,
 * los elementos visuales y la interacción del usuario.
 * 
 * @author Luisa Fernanda Henao Posada
 * @version 1.0
 * @since 20-05-2025
 */
public class JuegoPanel extends JPanel {
    private final List<Comida> comidas;
    private final List<Veneno> venenos;
    private int puntaje;
    private final Image fondo;

    /**
     * Constructor que inicializa el panel del juego.
     * Configura los elementos iniciales, listeners y hilos del juego.
     */
    public JuegoPanel() {
        this.comidas = Collections.synchronizedList(new ArrayList<>());
        this.venenos = Collections.synchronizedList(new ArrayList<>());
        this.puntaje = 0;

        // Carga la imagen de fondo
        fondo = new ImageIcon(getClass().getResource("/autonoma/AtrapaComida/images/Cielo.jpg")).getImage();
        setFocusable(true);

        // Configura el listener para clicks del mouse
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                manejarClic(e.getX(), e.getY());
            }
        });

        // Inicia los hilos para generar comida y veneno
        new Thread(new HiloComida(comidas, this)).start();
        new Thread(new HiloVeneno(venenos, this)).start();

        // Temporizador para repintar el componente
        Timer timer = new Timer(10, e -> repaint());
        timer.start();

        // Hilo adicional para repintar continuamente
        new Thread(() -> {
            while (true) {
                repaint();
                try {
                    Thread.sleep(30);
                } catch (InterruptedException ignored) {}
            }
        }).start();
    }

    /**
     * Maneja el evento de clic del mouse, verificando si se hizo clic
     * sobre comida o veneno y actualizando el puntaje correspondientemente.
     *
     * @param x Coordenada X del clic
     * @param y Coordenada Y del clic
     */
    private void manejarClic(int x, int y) {
        synchronized (comidas) {
            Iterator<Comida> it = comidas.iterator();
            while (it.hasNext()) {
                Comida comida = it.next();
                if (comida.contiene(x, y)) {
                    puntaje += 1;
                    it.remove();
                    return;
                }
            }
        }

        synchronized (venenos) {
            Iterator<Veneno> it = venenos.iterator();
            while (it.hasNext()) {
                Veneno veneno = it.next();
                if (veneno.contiene(x, y)) {
                    puntaje -= 2;
                    it.remove();
                    return;
                }
            }
        }
    }

    /**
     * Método generado automáticamente para inicializar componentes.
     * No modificar este método manualmente.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 533, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 398, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    /**
     * Método sobrescrito para pintar los componentes del juego.
     * Dibuja el fondo, los elementos de comida y veneno, y el marcador de puntaje.
     *
     * @param g Objeto Graphics para dibujar los componentes
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Dibuja el fondo
        g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);

        // Dibuja todas las comidas
        synchronized (comidas) {
            for (Comida comida : comidas) {
                comida.dibujar(g);
            }
        }

        // Dibuja todos los venenos
        synchronized (venenos) {
            for (Veneno veneno : venenos) {
                veneno.dibujar(g);
            }
        }

        // Dibuja el marcador de puntaje
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.10f));
        g2d.setColor(new Color(255, 255, 255)); 
        g2d.fillRoundRect(10, 10, 150, 35, 15, 15);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        g2d.setColor(Color.DARK_GRAY);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRoundRect(10, 10, 150, 35, 15, 15);
        g2d.setFont(new Font("Verdana", Font.BOLD, 24));
        g2d.setColor(Color.BLACK);
        g2d.drawString("Puntaje: " + puntaje, 17, 35); 
        g2d.setColor(new Color(153, 26, 153)); 
        g2d.drawString("Puntaje: " + puntaje, 15, 33);
    }
}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

