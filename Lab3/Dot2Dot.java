/*
 * Course: CS2852
 * Spring 2019
 * Lab: Lab 1 - Dot 2 Dot Generator
 * Name: Stuart Harley
 * Created: 3/5/19
 */

package harleys;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class runs the Dot2Dot program
 */
public class Dot2Dot extends Application {

    /**
     * Start method of the JavaFX program
     * @param stage the stage
     * @throws IOException if there is a problem loading Dot2Dot.fxml
     */
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Dot2Dot.fxml"));
        stage.setTitle("Dot to Dot");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
