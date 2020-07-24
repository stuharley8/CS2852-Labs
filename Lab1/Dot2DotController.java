/*
 * Course: CS2852
 * Spring 2019
 * Lab: Lab 1 - Dot 2 Dot Generator
 * Name: Stuart Harley
 * Created: 3/5/19
 */

package harleys;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.InputMismatchException;

/**
 * Controller class for the Dot2Dot fxml
 */
public class Dot2DotController {

    private static Picture picture = new Picture();

    private static final Alert ALERT = new Alert(Alert.AlertType.ERROR);

    @FXML
    private Canvas canvas;

    @FXML
    private Menu draw;

    /**
     * Loads the .dot file and displays the dots in the file and connects
     * neighboring dots with lines
     * @param actionEvent the ActionEvent
     */
    @FXML
    public void open(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("File Chooser");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                "Dot Files", "*.dot"));
        try {
            File file = fileChooser.showOpenDialog(null);
            Path path = file.toPath();
            picture.load(path);
            canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            picture.drawDots(canvas);
            picture.drawLines(canvas);
            draw.setDisable(false);
        } catch(IOException e) {
            ALERT.setTitle("Error Dialog");
            ALERT.setHeaderText("File Load Error");
            ALERT.setContentText("Error Loading File");
            ALERT.showAndWait();
        } catch(InputMismatchException | NullPointerException |
                NumberFormatException | IndexOutOfBoundsException e) {
            ALERT.setTitle("Error Dialog");
            ALERT.setHeaderText("File Load Error");
            ALERT.setContentText("File Data Incorrectly Formatted");
            ALERT.showAndWait();
        }
    }

    /**
     * Exits the program
     * @param actionEvent the ActionEvent
     */
    @FXML
    public void close(ActionEvent actionEvent) {
        Platform.exit();
    }

    /**
     * Redraws the loaded picture drawing only dots (no lines)
     * @param actionEvent the ActionEvent
     */
    @FXML
    public void drawOnlyDots(ActionEvent actionEvent) {
        try {
            canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            picture.drawDots(canvas);
        } catch(NullPointerException e) {
            ALERT.setTitle("Error Dialog");
            ALERT.setHeaderText("Drawing Error");
            ALERT.setContentText("You must open a file first");
            ALERT.showAndWait();
        }
    }

    /**
     * Redraws the loaded picture drawing only lines (no dots)
     * @param actionEvent the ActionEvent
     */
    @FXML
    public void drawOnlyLines(ActionEvent actionEvent) {
        try {
            canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            picture.drawLines(canvas);
        } catch(NullPointerException e) {
            ALERT.setTitle("Error Dialog");
            ALERT.setHeaderText("Drawing Error");
            ALERT.setContentText("You must open a file first");
            ALERT.showAndWait();
        }
    }
}
