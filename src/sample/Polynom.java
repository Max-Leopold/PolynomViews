package sample;

import javafx.scene.paint.Color;

import java.util.Observable;

public class Polynom extends Observable {

    double a;
    double b;
    double c;
    double d;

    public Polynom(double a, double b, double c, double d){

        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public void setA(double a) {
        this.a = a;
        setUpdate();
    }

    public void setB(double b) {
        this.b = b;
        setUpdate();
    }

    public void setC(double c) {
        this.c = c;
        setUpdate();
    }

    public void setD(double d) {
        this.d = d;
        setUpdate();
    }

    public double getValueY(int i){
        return (a + b * i + c * Math.pow(i, 2) + d * Math.pow(i, 3));
    }

    /**
     * Update Methode für das Polynom, sodass die Views benachrichtigt werden, soabld sich etwas ändert
     */
    public void setUpdate(){
        setChanged();
        notifyObservers();
    }
}
