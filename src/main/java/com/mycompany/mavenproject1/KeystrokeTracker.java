/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

/**
 *
 * @author super
 */
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class KeystrokeTracker extends HBox {

    private int correctCount = 0;
    private int incorrectCount = 0;

    private final Label correctLabel = new Label("Correct: 0");
    private final Label incorrectLabel = new Label("Incorrect: 0");

    public KeystrokeTracker() {

        this.setSpacing(25);
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-background-color: #f8f9fa; -fx-padding: 8px 15px; -fx-border-color: #e9ecef; -fx-border-radius: 5px;");

        correctLabel.setFont(Font.font("System", FontWeight.BOLD, 13));
        correctLabel.setStyle("-fx-text-fill: #2b9348;"); // Professional green color for correctness

        incorrectLabel.setFont(Font.font("System", FontWeight.BOLD, 13));
        incorrectLabel.setStyle("-fx-text-fill: #d90429;"); // Clear warning red color for mistakes

        this.getChildren().addAll(correctLabel, incorrectLabel);
    }

    /**
     * Increments the correct keystroke count metrics tracking tally.
     */
    public void logCorrectHit() {
        correctCount++;
        correctLabel.setText("Correct: " + correctCount);
    }

    /**
     * Increments the incorrect keystroke count metrics tracking tally.
     */
    public void logIncorrectMiss() {
        incorrectCount++;
        incorrectLabel.setText("Incorrect: " + incorrectCount);
    }

    /**
     * Resets metrics back to zero.
     */
    public void resetMetrics() {
        correctCount = 0;
        incorrectCount = 0;
        correctLabel.setText("Correct: 0");
        incorrectLabel.setText("Incorrect: 0");
    }
}
