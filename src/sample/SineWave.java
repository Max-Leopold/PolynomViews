package sample;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class SineWave extends JFrame {
    SineDraw sines = new SineDraw();
    JSlider cycles = new JSlider (1, 30, 5);
    public SineWave() {
        Container cp = getContentPane();
        cp.add (BorderLayout.CENTER, sines); cycles.addChangeListener (new ChangeListener(){
            public void stateChanged(ChangeEvent e) { sines.setCycles(
                    ((JSlider)e.getSource()).getValue());
                // Anwendungs-Panel
// Schiebe-Regler
                // Fenster-Container
// Panel hinzufuegen
// anonyme Klasse als AL
                // Wert setzen
// Regler hinzufuegen
            } });
        cp.add (BorderLayout.SOUTH, cycles); setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); setSize(300, 200);
        setLocation(250, 340);
        setTitle("SineWave");
        setVisible(true);
    }
    public static void main(String[] args) { new SineWave();
    }
}