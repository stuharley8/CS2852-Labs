/*
 * Course: CS2852
 * Spring 2019
 * Lab: Lab 9 - AutoComplete Revisited
 * Name: Stuart Harley
 * Created: 5/7/19
 */

package harleys;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Controller Class for the Auto Complete GUI
 */
public class AutoCompleteController {

    private static final Alert ALERT = new Alert(Alert.AlertType.ERROR);

    private AutoCompleter dictionary = new IndexDictionary(new ArrayList<>());

    private static String filename;

    private static boolean initialized = false;

    @FXML
    private Label matchesLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private TextArea matchesTextArea;

    @FXML
    private TextField searchBar;

    /**
     * FXML method dealing with changing the strategy of searching for matches
     * @param actionEvent the ActionEvent
     */
    @FXML
    private void updateStrategy(ActionEvent actionEvent) {
        AutoCompleter newDictionary = null;
        String strategy = actionEvent.getSource().toString();
        if(strategy.contains("Index")) {
            if(strategy.contains("arrayList")) {
                newDictionary = new IndexDictionary(new ArrayList<>());
            } else if(strategy.contains("linkedList")) {
                newDictionary = new IndexDictionary(new LinkedList<>());
            }
        } else if(strategy.contains("Enhanced")){
            if(strategy.contains("arrayList")) {
                newDictionary = new EnhancedDictionary(new ArrayList<>());
            } else if(strategy.contains("linkedList")) {
                newDictionary = new EnhancedDictionary(new LinkedList<>());
            }
        } else if(strategy.contains("sorted")) {
            newDictionary = new SortedDictionary(new SortedArrayList<>());
        } else if(strategy.contains("trie")) {
            newDictionary = new TrieAutoCompleter();
        }
        dictionary = newDictionary;
        if(initialized) {
            try {
                dictionary.initialize(filename);
                updateTimeLabel();
            } catch(IOException e) {
                ALERT.setTitle("Error Dialog");
                ALERT.setHeaderText("File Load Error");
                ALERT.setContentText("Error Loading File");
                ALERT.showAndWait();
            }
        }
        searchBar.clear();
        matchesTextArea.clear();
        matchesLabel.setText("Matches Found: 0");
    }

    /**
     * FXML method linked to the open menu item
     * @param actionEvent the ActionEvent
     */
    @FXML
    private void open(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("File Chooser");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                "Dictionary Files", "*.txt", "*.csv"));
        try {
            File file = fileChooser.showOpenDialog(null);
            if(file != null) {
                filename = file.toPath().toAbsolutePath().toString();
                dictionary.initialize(filename);
                updateTimeLabel();
                searchBar.clear();
                matchesTextArea.clear();
                matchesLabel.setText("Matches Found: 0");
                initialized = true;
            }
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
     * Shows all words that begin with the letters entered in the search bar
     * in the text area below
     * @param keyEvent the KeyEvent
     */
    @FXML
    private void showMatches(KeyEvent keyEvent) {
        if(!searchBar.getText().equals("")) {
            try {
                matchesTextArea.clear();
                List<String> matches = dictionary.allThatBeginWith(searchBar.getText().toLowerCase());
                StringBuilder stringBuilder = new StringBuilder();
                for (String word : matches) {
                    stringBuilder.append(word + "\n");
                }
                matchesTextArea.setText(stringBuilder.toString());
                matchesLabel.setText("Matches Found: " + matches.size());
                updateTimeLabel();
            } catch (IllegalStateException e) {
                ALERT.setTitle("Error Dialog");
                ALERT.setHeaderText("Operation Error");
                ALERT.setContentText("Must open a dictionary before searching for matches");
                searchBar.clear();
                ALERT.showAndWait();
            }
        } else {
            matchesTextArea.clear();
            matchesLabel.setText("Matches Found: 0");
            timeLabel.setText("Time required: 0.0 milliseconds");
        }
    }

    /**
     * Updates the Time required label and displays it formatted
     */
    private void updateTimeLabel() {
        long time = dictionary.getLastOperationTime();
        long min = TimeUnit.NANOSECONDS.toMinutes(time);
        long sec = TimeUnit.NANOSECONDS.toSeconds(time - TimeUnit.MINUTES.toNanos(min));
        long millis = TimeUnit.NANOSECONDS.toMillis(time - TimeUnit.MINUTES.toNanos(min)
                - TimeUnit.SECONDS.toNanos(sec));
        long micros = TimeUnit.NANOSECONDS.toMicros(time - TimeUnit.MINUTES.toNanos(min)
                - TimeUnit.SECONDS.toNanos(sec) - TimeUnit.MILLISECONDS.toNanos(millis));
        long ns = (time - TimeUnit.MINUTES.toNanos(min) - TimeUnit.SECONDS.toNanos(sec)
                - TimeUnit.MILLISECONDS.toNanos(millis) - TimeUnit.MICROSECONDS.toNanos(micros));
        if(micros == 0) {
            timeLabel.setText("Time required: " + ns + " nanoseconds");
        } else if(millis == 0) {
            timeLabel.setText(String.format("Time required: %3d.%03d", micros, ns) + " microseconds");
        } else if(sec == 0) {
            timeLabel.setText(String.format("Time required: %3d.%03d", millis, micros) + " milliseconds");
        } else {
            timeLabel.setText(String.format("Time required: %02d:%02d.%03d", min, sec, millis));
        }
    }
}