/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 *
 * @author super
 */
public class PhysicalInputHandler extends VBox {

    private final Label physicalInputLabel = new Label("");
    private final Label statusLabel = new Label("");

    public PhysicalInputHandler() {
        this.setSpacing(5);
        this.setAlignment(Pos.CENTER_LEFT);

        Label descriptionLabel = new Label("Physical Keyboard Response:");
        descriptionLabel.setFont(Font.font("System", 12));

        physicalInputLabel.setFont(Font.font("System", 14));
        physicalInputLabel.setPrefWidth(610);
        physicalInputLabel.setMinHeight(30);
        physicalInputLabel.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-border-radius: 3px; -fx-padding: 5px;");

        statusLabel.setFont(Font.font("System", 12));
        statusLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");

        this.getChildren().addAll(descriptionLabel, physicalInputLabel, statusLabel);
    }

    /**
     * Attaches listeners to tell when a key on the keyboard is pressed.
     * @param scene The scene that shows the typing.
     * @param mainApp The app that has the target text.
     * @param tracker The tracker that keeps track of correct and incorrect keystrokes.
     */
    public void attachKeyboardListeners(Scene scene, App mainApp, KeystrokeTracker tracker) {
        String pressedStyle = "-fx-background-color: #FFB703; -fx-text-fill: black; -fx-scale-x: 0.95; -fx-scale-y: 0.95;";
        String normalStyle = "";

        scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            KeyCode code = event.getCode();
            String codeName = code.toString();
            boolean isKeyHandled = false;

            String currentText = physicalInputLabel.getText();
            String targetText = mainApp.targetTextLabel.getText();

            if (code == KeyCode.SHIFT) {
                isKeyHandled = true;
                if (!mainApp.isShiftActive) {
                    mainApp.toggleShift();
                }
            }

            if (code == KeyCode.BACK_SPACE) {
                isKeyHandled = true;
                if (!currentText.isEmpty()) {
                    physicalInputLabel.setText(currentText.substring(0, currentText.length() - 1));
                }
            } else if (code == KeyCode.SPACE) {
                isKeyHandled = true;
                
                if (currentText.length() < targetText.length() && targetText.charAt(currentText.length()) == ' ') {
                    tracker.logCorrectHit();
                } else {
                    tracker.logIncorrectMiss();
                }
                
                physicalInputLabel.setText(currentText + " ");
                
            } else if (code != KeyCode.SHIFT) {
                String typedChar = event.getText();
                if (event.isShiftDown()) {
                    typedChar = typedChar.toUpperCase();
                } else {
                    typedChar = typedChar.toLowerCase();
                }

                if (!typedChar.isEmpty() && (typedChar.matches("[a-zA-Z0-9]") || typedChar.equals(",") || typedChar.equals("."))) {
                    isKeyHandled = true;
                    
                    if (currentText.length() < targetText.length() && targetText.charAt(currentText.length()) == typedChar.charAt(0)) {
                        tracker.logCorrectHit();
                    } else {
                        tracker.logIncorrectMiss();
                    }
                    
                    physicalInputLabel.setText(currentText + typedChar);
                }
            }

            if (codeName.equals("COMMA")) {
                codeName = ",";
            } else if (codeName.equals("PERIOD")) {
                codeName = ".";
            } else if (codeName.startsWith("DIGIT")) {
                codeName = codeName.substring(5);
            }

            Button virtualBtn = mainApp.getKeyButtonMap().get(codeName);
            if (virtualBtn == null && code == KeyCode.SPACE) {
                virtualBtn = mainApp.getKeyButtonMap().get("Space");
            }
            
            if (virtualBtn != null) {
                isKeyHandled = true;
                virtualBtn.setStyle(pressedStyle);
            }

            if (!isKeyHandled) {
                statusLabel.setText("Not handled");
            } else {
                statusLabel.setText("");
            }
        });

        scene.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            KeyCode code = event.getCode();
            String codeName = code.toString();

            if (code == KeyCode.SHIFT) {
                if (mainApp.isShiftActive) {
                    mainApp.toggleShift();
                }
            }

            if (codeName.equals("COMMA")) {
                codeName = ",";
            } else if (codeName.equals("PERIOD")) {
                codeName = ".";
            } else if (codeName.startsWith("DIGIT")) {
                codeName = codeName.substring(5);
            }

            Button virtualBtn = mainApp.getKeyButtonMap().get(codeName);
            if (virtualBtn == null && code == KeyCode.SPACE) {
                virtualBtn = mainApp.getKeyButtonMap().get("Space");
            }
            if (virtualBtn != null) {
                virtualBtn.setStyle(normalStyle);
            }
            
        });
    }

    public void clearInputLabel() {
        this.physicalInputLabel.setText("");
        this.statusLabel.setText("");
    }

    public String getTypedText() {
        return physicalInputLabel.getText();
    }
}

