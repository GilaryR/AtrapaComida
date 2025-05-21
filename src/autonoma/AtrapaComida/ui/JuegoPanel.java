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

public class JuegoPanel extends JPanel {
    private final List<Comida> comidas;
    private final List<Veneno> venenos;
    private int puntaje;
    private final Image fondo;


    public JuegoPanel() {
        this.comidas = Collections.synchronizedList(new ArrayList<>());
        this.venenos = Collections.synchronizedList(new ArrayList<>());
        this.puntaje = 0;

        fondo = new ImageIcon(getClass().getResource("/autonoma/ComidaQueCae/images/Cielo.jpg")).getImage();
        setFocusable(true);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                manejarClic(e.getX(), e.getY());
            }
        });

        new Thread(new HiloComida(comidas, this)).start();
        new Thread(new HiloVeneno(venenos, this)).start();
        {
        Timer timer = new Timer(10, e -> repaint());
        timer.start();
    }

        new Thread(() -> {
            while (true) {
                repaint();
                try {
                    Thread.sleep(30);
                } catch (InterruptedException ignored) {}
            }
        }).start();
    }
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
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);

        synchronized (comidas) {
            for (Comida comida : comidas) {
                comida.dibujar(g);
            }
        }

        synchronized (venenos) {
            for (Veneno veneno : venenos) {
                veneno.dibujar(g);
            }
        }
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

