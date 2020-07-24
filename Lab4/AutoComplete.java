/*
 * Course: CS2852
 * Spring 2019
 * Lab: Lab 4 - AutoComplete
 * Name: Stuart Harley
 * Created: 3/22/19
 */

package harleys;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class runs the AutoComplete program
 */
public class AutoComplete extends Application {

    /**
     * Start method of the JavaFXML program
     * @param stage the stage
     * @throws IOException if there is a problem loading AutoComplete.fxml
     */
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AutoComplete.fxml"));
        stage.setTitle("Auto Complete");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
