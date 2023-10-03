package com.sierpinskitriangle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("application.fxml"));
        Image icon = new Image(getClass().getResourceAsStream("icon.png"));

        Scene scene = new Scene(root);
        stage.setAlwaysOnTop(false);
        stage.setResizable(false);
        stage.setTitle("Sierpinski Drawer");
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();
        stage.requestFocus();
    }

    public static void main(String[] args) {
        launch();
    }
}