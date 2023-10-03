package com.sierpinskitriangle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;


public class paintController implements Initializable {

    ArrayList<Point> Eckpunkte = new ArrayList<Point>();
    ArrayList<Point> InnerePunkte = new ArrayList<Point>();

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private Button clearButton;

    @FXML
    private Button triangleCreateButton;

    @FXML
    private TextField scale;

    @FXML
    private Canvas canvas;

    @FXML
    private Slider drawSpeedSlider;

    GraphicsContext paintTool;
    

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        paintTool = canvas.getGraphicsContext2D();
        scale.setPromptText("Enter Magnitude 1 - 2876");
        triangleCreateButton.setDisable(true);
        drawSpeedSlider.setDisable(true);


        canvas.setOnMouseClicked(mouseEvent -> {
            int x = (int) mouseEvent.getX();
            int y = (int) mouseEvent.getY();

            Eckpunkte.add(new Point(x, y));
            if (Eckpunkte.size() > 3){
                InnerePunkte.add(Eckpunkte.get(3));
            }
            if (InnerePunkte.size() == 1){
                triangleCreateButton.setDisable(false);
                drawSpeedSlider.setDisable(false);
            }
            paintTool.setFill(colorPicker.getValue());
            paintTool.fillRoundRect(x, y, 1, 1, 1, 1);
            });

    }


    @FXML
    public void clearCanvas(ActionEvent e){
        clearButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                paintTool.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                Eckpunkte.clear();
                InnerePunkte.clear();
            }
        });
    }

    public void createSierpinskiTriangle(ActionEvent e){
        triangleCreateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    createTrianglePunkte(setScale());
                    drawPoints();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

            }

        });
        triangleCreateButton.isDisabled();
    }


    public void createTrianglePunkte(long n) throws InterruptedException {
        if (n > 0){
            Random random = new Random();
            int startIndex = random.nextInt(2 + 1);
            int xPunktEcke = (int) Eckpunkte.get(startIndex).getX();
            int yPunktEcke = (int) Eckpunkte.get(startIndex).getY();

            int xPunktNeu = (int) InnerePunkte.get(InnerePunkte.size() - 1).getX();
            int yPunktNeu = (int) InnerePunkte.get(InnerePunkte.size() - 1).getY();


            Point tempPunkt = new Point((xPunktEcke + xPunktNeu) / 2, (yPunktEcke + yPunktNeu) / 2);
            InnerePunkte.add(tempPunkt);
            n--;
            createTrianglePunkte(n);
        }
    }

    public void drawPoints(){
        for (Point point : InnerePunkte) {
            paintTool.fillRoundRect(point.getX(), point.getY(), 1, 1, 1, 1);
        }
    }

    public int setScale(){

        if (Objects.equals(scale.getText(), "")){
            return 100;
        } else return Math.min(parseInt(scale.getText()), 2876);
    }

    public double setDrawSpeed() throws InterruptedException {
        createTrianglePunkte((long) drawSpeedSlider.getValue());
        return drawSpeedSlider.getValue();
    }

}