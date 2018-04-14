

import java.util.Observable;

public class Polynom extends Observable {

    double a;
    double b;
    double c;
    double d;

    //initialisierung des Polynoms mit übergebenen Werten
    public Polynom(double a, double b, double c, double d){

        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    /**
     *Ändert a zu dem übergebenen Wert und ruft die setUpdate Methode auf
     * @param a Wert zu dem a geändert werden soll
     */
    public void setA(double a) {
        this.a = a;
        setUpdate();
    }

    /**
     *Ändert b zu dem übergebenen Wert und ruft die setUpdate Methode auf
     * @param b Wert zu dem b geändert werden soll
     */
    public void setB(double b) {
        this.b = b;
        setUpdate();
    }

    /**
     *Ändert c zu dem übergebenen Wert und ruft die setUpdate Methode auf
     * @param c Wert zu dem c geändert werden soll
     */
    public void setC(double c) {
        this.c = c;
        setUpdate();
    }

    /**
     *Ändert d zu dem übergebenen Wert und ruft die setUpdate Methode auf
     * @param d Wert zu dem d geändert werden soll
     */
    public void setD(double d) {
        this.d = d;
        setUpdate();
    }

    /**
     * Berechnet Y Wert des Polynoms an der Stelle i
     * @param i
     * @return Y Wert des Polynoms an der Stelle i
     */
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
