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
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Controller class for the Dot2Dot fxml
 */
public class Dot2DotController {

    /**
     * Picture instance used in showing the picture and editing
     */
    private Picture picture;

    /**
     * Picture instance used to reset picture to it's original state
     * so that the changeNumDots method can set any number of dots
     * less than the original picture contains
     */
    private Picture originalPicture;

    private static final Alert ALERT = new Alert(Alert.AlertType.ERROR);

    @FXML
    private Canvas canvas;

    @FXML
    private Menu draw;

    @FXML
    private Label numDotsLabel;

    @FXML
    private MenuItem save;

    /**
     * Clears the canvas of the current picture
     * @param canvas the canvas to be cleared
     */
    private static void clearCanvas(Canvas canvas) {
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    /**
     * Updates the numDotsLabel text displaying the number of dots in the picture
     */
    @FXML
    private void updateNumDotsLabel() {
        numDotsLabel.setText("Number of Dots: " + picture.getNumDots());
    }

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
            originalPicture = new Picture(new ArrayList<>());
            originalPicture.load(path);
            picture = originalPicture;
            clearCanvas(canvas);
            picture.drawDots(canvas);
            picture.drawLines(canvas);
            draw.setDisable(false);
            save.setDisable(false);
            updateNumDotsLabel();
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
     * Saves the current picture to a .dot file
     * @param actionEvent the ActionEvent
     */
    @FXML
    public void save(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Dot File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                "Dot File", "*.dot"));
        File file = fileChooser.showSaveDialog(null);
        if(file != null) {
            try {
                picture.save(file.toPath());
            } catch(IOException e) {
                ALERT.setTitle("Error Dialog");
                ALERT.setHeaderText("File Save Error");
                ALERT.setContentText("Error Saving File");
                ALERT.showAndWait();
            }
        }
    }

    /**
     * Redraws the loaded picture drawing only dots (no lines)
     * @param actionEvent the ActionEvent
     */
    @FXML
    public void drawOnlyDots(ActionEvent actionEvent) {
        try {
            clearCanvas(canvas);
            picture.drawDots(canvas);
        } catch(NullPointerException e) {
            ALERT.setTitle("Error Dialog");
            ALERT.setHeaderText("Drawing Error");
            ALERT.setContentText("You Must Open A File First");
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
            clearCanvas(canvas);
            picture.drawLines(canvas);
        } catch(NullPointerException e) {
            ALERT.setTitle("Error Dialog");
            ALERT.setHeaderText("Drawing Error");
            ALERT.setContentText("You Must Open A File First");
            ALERT.showAndWait();
        }
    }

    /**
     * Allows the user to enter a specified number of dots. Removes dots from the picture
     * until the specified number remains and displays the updated picture.
     * @param actionEvent the ActionEvent
     */
    @FXML
    public void changeNumDots(ActionEvent actionEvent) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Dot Number Changer");
        dialog.setHeaderText("How Many Dots Should Remain In The Picture?");
        dialog.setContentText("Number Of Dots: ");
        Optional<String> input = dialog.showAndWait();
        try {
            int numDots = Integer.parseInt(input.get());
            picture = new Picture(originalPicture, new ArrayList<>());
            picture.removeDots(numDots);
            clearCanvas(canvas);
            picture.drawDots(canvas);
            picture.drawLines(canvas);
            updateNumDotsLabel();
        } catch(NullPointerException | InputMismatchException | IllegalArgumentException e) {
            ALERT.setTitle("Error Dialog");
            ALERT.setHeaderText("Input Error");
            ALERT.setContentText("Invalid Number Of Dots");
            ALERT.showAndWait();
        } catch(NoSuchElementException e) {
            return;
        }
    }
}
