package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer, EventHandler<ActionEvent> {

    //Hinzufügen aller Scene Builder elemente

    @FXML
    VBox vBox;

    @FXML
    Pane pane1; //Pane für Graph 1

    @FXML
    Pane pane2; //Pane für Graph 2

    //Slider für verschiedene Werte des Polynoms
    @FXML
    Slider konstante;

    @FXML
    Slider linearerKoeffizient;

    @FXML
    Slider quadratischerKoeffizient;

    @FXML
    Slider kubischerKoeffizient;

    //Menü festlegen mit zwei Menüs in einer Menübar für Graph 1 und 2
    @FXML
    Menu rotateMenu1;

    @FXML
    MenuItem rotateLeft1;

    @FXML
    MenuItem rotateRight1;

    @FXML
    MenuItem rotateLeft2;

    @FXML
    MenuItem rotateRight2;

    @FXML
    MenuItem pickColor1;

    @FXML
    MenuItem pickColor2;

    @FXML
    MenuItem scaleD2;

    @FXML
    MenuItem scaleM2;

    @FXML
    MenuItem scaleD1;

    @FXML
    MenuItem scaleM1;

    private Point2D[] points; //Array für zu Zeichnende Punkte mit X und Y Koordinate des Punkts

    private Polynom polynom = new Polynom(1, 1, 1, 1); //Erstellen eines Polynoms

    private Color color1 = Color.RED; //Farbe des Graph 1
    private Color color2 = Color.BLUE; //Farbe des Graph 2

    private int scaleFactor1 = 100; //Scalefactor für Graph 1
    private int scalefactor2 = 10; //Scalefactor für Graph 2

    private int rotation1 = 0; //Rotation für Graph 1
    private int rotation2 = 0; //Rotation für Graph 2

    public void initialize(){

        //Hinzufügen von ChangeListenern für jeden Slider
        //Konstanten Slider
        konstante.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                polynom.setA((double) newValue);
            }
        });

        //Linearer Koeffizient Slider
        linearerKoeffizient.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                polynom.setB((double) newValue);
            }
        });

        //Quadratischer Koeffizient Slider
        quadratischerKoeffizient.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                polynom.setC((double) newValue);
            }
        });

        //Kubischer Koeffizient Slider
        kubischerKoeffizient.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                polynom.setD((double) newValue);
            }
        });

        //Polynom als Observer hinzufügen
        polynom.addObserver(this);

        //Hinzufügen von Eventhandlern für die Menüknöpfe
        rotateLeft1.setOnAction(this);
        rotateRight1.setOnAction(this);

        rotateLeft2.setOnAction(this);
        rotateRight2.setOnAction(this);

        pickColor1.setOnAction(this);
        pickColor2.setOnAction(this);

        scaleM2.setOnAction(this);
        scaleD2.setOnAction(this);

        scaleD1.setOnAction(this);
        scaleM1.setOnAction(this);


    }

    /**
     * Update Methode. Wird aufgerufen wenn etwas in Polynom geändert wird.
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        repaint(pane1, color1, scaleFactor1, rotation1);
        repaint(pane2, color2, scalefactor2, rotation2);
    }

    /**
     * Paint funktion für die Graphen
     * @param p Pane auf das gezeichnet werden soll
     * @param color Farbe des Graphen
     * @param scaleFactor Scalefaktor des Graphen
     * @param rotation Rotation des Graphen
     */
    private void repaint(Pane p, Color color, int scaleFactor, int rotation) {

        //Entfernen der vorherigen Graphen der auf dem Pane gezeichnet wurde
        p.getChildren().clear();

        //Erstellen eines Canvas mit der Größe des Pane
        Canvas canvas = new Canvas(p.getWidth(), p.getHeight());
        GraphicsContext gc = canvas.getGraphicsContext2D();

        //Erstellen des Arrays welches X und Y Position der Punkte speichert
        points = new Point2D[scaleFactor * 20];

        //Rotation des Pane festlegen
        p.setRotate(rotation);

        //Wenn das Pane eine Rotation von 90 oder 270 hat muss das Pane kleine gemacht werden um den anderen Graphen nicht zu überdecken
        if(rotation == 90 || rotation == 270){
            p.setMaxSize(p.getHeight(), p.getWidth());
        }
        else{
            p.setMaxSize(500, 178);
        }

        //Hinzufügen der Punkte in Point2D Array
        for (int i = 0; i < scaleFactor * 20; i ++) {
            double valY = polynom.getValueY(i - scaleFactor / 2); //Zentrieren -> Mitte des Panes soll Ursprung des Graph sein
            points[i] = new Point2D(i * p.getWidth() / scaleFactor , -valY + p.getHeight() / 2); //Hinzufügen der Punkte in Array
        }

        for (int i = 0; i < 1; i++) {
            //Zeichnen der ersten Linie am linken rand
            gc.strokeLine(points[i].getX(), points[i].getY(), points[i + 1].getX(), points[i + 1].getY());

            gc.setStroke(color);

        }

        //Zeichnen aller anderen Linien vom Punkt bis zu dessen linken Nachbarn
        for (int i = 1; i < scaleFactor * 20; i++) {

            gc.strokeLine(points[i].getX(), points[i].getY(), points[i - 1].getX(), points[i - 1].getY());

            gc.setStroke(color);
        }


        //Hinzufügen des Canvas auf den ausgewählten Pane
        p.getChildren().add(canvas);
    }


    @Override
    public void handle(ActionEvent event) {

        String message = event.getSource().toString();
        ColorPicker colorPicker = new ColorPicker();

        //Überprüfen welcher Menü Button gedrpckt wurde
        if(message.contains("rotateLeft1")){
            //rotation links
            if(rotation1 == 0){
                rotation1 = 270;
            }
            else{
                rotation1 -= 90;
            }
        }
        else if(message.contains("rotateRight1")){
            //rotation rechts
            if(rotation1 == 270){
                rotation1 = 0;
            }
            else{
                rotation1 += 90;
            }
        }
        else if(message.contains("rotateLeft2")){
            //rotation links
            if(rotation2 == 0){
                rotation2 = 270;
            }
            else{
                rotation2 -= 90;
            }
        }
        else if(message.contains("rotateRight2")){
            //rotation rechts
            if(rotation2 == 270){
                rotation2 = 0;
            }
            else{
                rotation2 += 90;
            }
        }
        else if(message.contains("pickColor1")){ //ColorPicker 1

            VBox vb = new VBox();
            Scene scene = new Scene(vb);
            vb.getChildren().add(colorPicker);
            Stage stage = new Stage(); //Erstellen einer neuen Stage mit VBox und ColorPicker
            stage.setScene(scene);
            stage.setX(300);
            stage.show();

            //EventHandler des ColorPicker
            colorPicker.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    color1 = colorPicker.getValue();
                    stage.close(); //Sobald Farbe ausgewählt wurde schließen des Fenstern mit ColorPicker
                    polynom.setUpdate();
                }
            });
        }
        else if(message.contains("pickColor2")){ //ColorPicker 2

            VBox vb = new VBox();
            Scene scene = new Scene(vb);
            vb.getChildren().add(colorPicker);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setX(1000);
            stage.show();

            colorPicker.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    color2 = colorPicker.getValue();
                    stage.close();
                    polynom.setUpdate();
                }
            });
        }
        else if(message.contains("scaleD1")){ //Scale halbieren (Graph 1)

            if(scaleFactor1 > 1) {
                scaleFactor1 /= 2;
                polynom.setUpdate();
            }
        }
        else if(message.contains("scaleD2")){ //Scale halbieren (Graph 2)

            if(scalefactor2 > 1) {
                scalefactor2 /= 2;
                polynom.setUpdate();
            }
        }
        else if(message.contains("scaleM1")){ //Scale verdoppeln (Graph 1)
            scaleFactor1 *= 2;
            polynom.setUpdate();

        }
        else if(message.contains("scaleM2")){ //Scale verdoppeln (Graph 2)
            scalefactor2 *= 2;
            polynom.setUpdate();

        }


    }
}
