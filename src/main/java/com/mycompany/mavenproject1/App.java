package com.mycompany.mavenproject1;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {
    private final TextArea displayArea = new TextArea();
    
    // Tracking for the shift button.
    private boolean isShiftActive = false;
    private Button shiftButton;

    //Keyboard layout, in four rows
    private final String[] row1 = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "Backspace"};
    private final String[] row2 = {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"};
    private final String[] row3 = {"A", "S", "D", "F", "G", "H", "J", "K", "L"};
    private final String[] row4 = {"Z", "X", "C", "V", "B", "N", "M"};

    private final List<Button> letterButtons = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        displayArea.setPrefRowCount(4);
        displayArea.setWrapText(true);
        displayArea.setEditable(false);

        VBox mainLayout = new VBox(15);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.CENTER);

        VBox keyboardLayout = new VBox(8);
        keyboardLayout.setAlignment(Pos.CENTER);

        keyboardLayout.getChildren().add(createKeyboardRow(row1));
        keyboardLayout.getChildren().add(createKeyboardRow(row2));
        keyboardLayout.getChildren().add(createKeyboardRow(row3));
        
        //Keyboard row 4.
        HBox hBoxRow4 = createKeyboardRow(row4);
        shiftButton = new Button("Shift");
        shiftButton.setPrefSize(80, 40);
        shiftButton.setStyle("-fx-font-weight: bold; -fx-background-color: #d1d1d1;");
        shiftButton.setOnAction(e -> toggleShift());
        hBoxRow4.getChildren().add(0, shiftButton); //Force the shift key to be at the start of row 4.
        keyboardLayout.getChildren().add(hBoxRow4);

        //The spacebar needs its own row.
        HBox hBoxRow5 = new HBox(8);
        hBoxRow5.setAlignment(Pos.CENTER);
        Button spaceButton = new Button("Space");
        spaceButton.setPrefSize(300, 40);
        spaceButton.setOnAction(e -> displayArea.appendText(" "));
        hBoxRow5.getChildren().add(spaceButton);
        keyboardLayout.getChildren().add(hBoxRow5);

        mainLayout.getChildren().addAll(displayArea, keyboardLayout);

        Scene scene = new Scene(mainLayout, 650, 450);
        primaryStage.setTitle("JavaFX Virtual Keyboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    /**
     * Helper method for handling removing text from the user's text field.
     */
    private void handleBackspace() {
        String currentText = displayArea.getText();
        if (!currentText.isEmpty()) {
            displayArea.setText(currentText.substring(0, currentText.length() - 1));
        }
    }
    
    /**
     * Generates a keyboard row with the includes keys.
     * Has special functionality for backspace and shift.
     * @param keys a string array depicting which keys are in the generated row.
     * @return a row of keys.
     */
    private HBox createKeyboardRow(String[] keys) {
        HBox row = new HBox(8);
        row.setAlignment(Pos.CENTER);

        for (String key : keys) {
            Button btn = new Button(key.toLowerCase());
            btn.setPrefSize(50, 40);
            
            // Backspace needs to be a larger size, mimicing proper keyboards.
            if (key.equals("Backspace")) {
                btn.setText("⌫");
                btn.setPrefSize(90, 40);
                btn.setOnAction(e -> handleBackspace());
            } else {
                letterButtons.add(btn);
                btn.setOnAction(e -> {
                    displayArea.appendText(btn.getText());
                    // Turns off the shift key once a key has been pressed.
                    if (isShiftActive) {
                        toggleShift();
                    }
                });
            }
            row.getChildren().add(btn);
        }
        return row;
    }
    
    private void toggleShift() {
        isShiftActive = !isShiftActive;
        
        if (isShiftActive) {
            shiftButton.setStyle("-fx-font-weight: bold; -fx-background-color: #3a86ff; -fx-text-fill: white;");
            for (Button btn : letterButtons) {
                btn.setText(btn.getText().toUpperCase());
            }
        } else {
            shiftButton.setStyle("-fx-font-weight: bold; -fx-background-color: #d1d1d1; -fx-text-fill: black;");
            for (Button btn : letterButtons) {
                btn.setText(btn.getText().toLowerCase());
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }

}