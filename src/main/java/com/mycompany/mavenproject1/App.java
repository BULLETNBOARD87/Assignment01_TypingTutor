package com.mycompany.mavenproject1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {
    private final Label targetTextLabel = new Label("The quick brown fox jumps over the lazy dog.");
    PhysicalInputHandler physicalPanel = new PhysicalInputHandler();
    private final Map<String, Button> keyButtonMap = new HashMap<>();
    
    private final String[] practicePhrases = {
        "The quick brown fox jumps over the lazy dog.",
        "JavaFX applications are modular and clean.",
        "Object oriented programming is powerful.",
        "Always practice typing to build muscle memory.",
        "Software engineers look for simple clean solutions.",
        "Keep code structured with distinct responsibilities."
    };
    
    private int currentPhraseIndex = 0;

    private final Label counterLabel = new Label("1 of 6");
    private Button nextButton;
    private Button resetButton;
    
    // Tracking for the shift button.
    public boolean isShiftActive = false;
    private Button shiftButton;
   
    //Keyboard layout, in four rows
    private final String[] row1 = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "Backspace"};
    private final String[] row2 = {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"};
    private final String[] row3 = {"A", "S", "D", "F", "G", "H", "J", "K", "L"};
    private final String[] row4 = {"Z", "X", "C", "V", "B", "N", "M"};

    private final List<Button> letterButtons = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        HBox controlBar = createControlBar(physicalPanel);
        
        targetTextLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        targetTextLabel.setStyle("-fx-text-fill: #555555; -fx-background-color: #f0f0f0; -fx-padding: 10px; -fx-background-radius: 5px;");
        targetTextLabel.setPrefWidth(610);
        targetTextLabel.setAlignment(Pos.CENTER_LEFT);

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
        keyButtonMap.put("SHIFT", shiftButton);
        shiftButton.setFocusTraversable(false);
        hBoxRow4.getChildren().add(0, shiftButton); //Force the shift key to be at the start of row 4.
        keyboardLayout.getChildren().add(hBoxRow4);

        //The spacebar needs its own row.
        HBox hBoxRow5 = new HBox(8);
        hBoxRow5.setAlignment(Pos.CENTER);
        Button spaceButton = new Button("Space");
        spaceButton.setPrefSize(300, 40);
        keyButtonMap.put("SPACE", spaceButton);
        spaceButton.setFocusTraversable(false);
        spaceButton.setMouseTransparent(true); 
        hBoxRow5.getChildren().add(spaceButton);
        keyboardLayout.getChildren().add(hBoxRow5);
        
        Scene scene = new Scene(mainLayout, 650, 450);
        physicalPanel.attachKeyboardListeners(scene, this);
        
        mainLayout.getChildren().addAll(targetTextLabel, physicalPanel, controlBar, keyboardLayout);
        primaryStage.setTitle("JavaFX Virtual Keyboard");
        primaryStage.setScene(scene);
        mainLayout.requestFocus();
        primaryStage.show();
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
        btn.setFocusTraversable(false);
        btn.setMouseTransparent(true); 

            if (key.equals("Backspace")) {
                btn.setText("⌫");
                btn.setPrefSize(90, 40);
                keyButtonMap.put("BACK_SPACE", btn);
            } else {
                letterButtons.add(btn);
                keyButtonMap.put(key.toUpperCase(), btn);
            }
            
        row.getChildren().add(btn);
        }
        return row;
    }
    
    /**
     * Placeholder
     */
    public void toggleShift() {
        isShiftActive = !isShiftActive;
        if (isShiftActive) {
            shiftButton.setStyle("-fx-font-weight: bold; -fx-background-color: #3a86ff; -fx-text-fill: white;");
            for (Button btn : letterButtons) {
                btn.setText(btn.getText().toUpperCase());
            }
        } else {
            shiftButton.setStyle(""); // Returns to default style
            for (Button btn : letterButtons) {
                btn.setText(btn.getText().toLowerCase());
            }
        }
    }
    
    /**
     * PLACEHOLDER
     * @param physicalPanel 
     */
    private void updateApplicationState(PhysicalInputHandler physicalPanel) {
        
    targetTextLabel.setText(practicePhrases[currentPhraseIndex]);
    
    physicalPanel.clearInputLabel();
    counterLabel.setText((currentPhraseIndex + 1) + " of " + practicePhrases.length);
}
    
    /**
     * PLACEHOLDER
     * @param physicalPanel
     * @return 
     */
    private HBox createControlBar(PhysicalInputHandler physicalPanel) {
    HBox controlBar = new HBox(15);
    controlBar.setAlignment(Pos.CENTER);
    
    // Configure Counter
    counterLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
    
    // Configure Next Button (Requirement 9 & 10)
    nextButton = new Button("Next");
    nextButton.setFocusTraversable(false); // Crucial: Prevents spacebar interception
    nextButton.setOnAction(e -> {
        if (currentPhraseIndex < practicePhrases.length - 1) {
            currentPhraseIndex++;
        } else {
            currentPhraseIndex = 0; // Wrap around to index 0 or keep last (assignment allows flexible text cycling)
        }
        updateApplicationState(physicalPanel);
    });

    // Configure Reset Button (Requirement 11)
    resetButton = new Button("Reset");
    resetButton.setFocusTraversable(false); // Crucial: Prevents spacebar interception
    resetButton.setOnAction(e -> {
        currentPhraseIndex = 0; // Return to input text 1
        updateApplicationState(physicalPanel);
    });

    controlBar.getChildren().addAll(counterLabel, nextButton, resetButton);
    return controlBar;
}

    public Map<String, Button> getKeyButtonMap() {
        return keyButtonMap;
    }
    
    public static void main(String[] args) {
        launch();
    }

    

}