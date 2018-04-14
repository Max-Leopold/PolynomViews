
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;

class SineDraw extends JPanel {
    static final int SCALEFACTOR = 20;
    int cycles;
    int points;
    double[] sines;
    int[] pts;
    SineDraw () { setCycles(2); }

    public void setCycles (int newCycles) {

        cycles = newCycles;
        points = SCALEFACTOR * cycles;
        sines = new double[points];
        pts = new int[points];

        for (int i = 0; i < points; i++) {
            double radians = (Math.PI/SCALEFACTOR) * i;
            sines[i] = Math.sin (radians);
        }
        repaint();
    }
    public void paintComponent(Graphics g) {

        super.paintComponent (g); // in Superklasse aufrufen ...
        int maxWidth = getWidth(); // Weite bestimmen
        double hstep = (double)maxWidth/(double)points; // horizontale Schrittweite
        int maxHeight = getHeight(); // Hoehe Bestimmen
        for (int i = 0; i < points; i++) // fuer alle Punkte:
            pts[i] = (int)(( 0.5 - sines[i] * 0.48) * maxHeight); // skalieren
        g.setColor (Color.red);
        for (int i = 1; i < points; i++) {
// Farbe setzen
// fuer alle Punkte (bis auf ersten): // bestimme x,y-Koordinaten
// des aktuellen Punkts und des
// linken Nachbarn
            int x1 = (int)((i - 1));
            int x2 = (int)(i );
            int y1 = pts[i-1];
            int y2 = pts[i]; //
            g.drawLine (x1, y1, x2, y2);
        }
    } // end paintComponent
}


