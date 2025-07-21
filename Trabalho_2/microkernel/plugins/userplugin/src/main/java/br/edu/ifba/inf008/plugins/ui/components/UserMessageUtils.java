package br.edu.ifba.inf008.plugins.ui.components;

import javafx.scene.control.Label;

public class UserMessageUtils {

    public static void displayErrorMessage(Label label, String message) {
        label.setStyle("-fx-text-fill: #d31414;");
        label.setText(message);
    }
    
    public static void displaySuccessMessage(Label label, String message) {
        label.setStyle("-fx-text-fill: #008800;");
        label.setText(message);
    }

    public static void displayConfirmationMessage(Label label, String message) {
        label.setStyle("-fx-text-fill: #d1a000ff;");
        label.setText(message);
    }

    public static void clearMessage(Label label) {
        label.setText("");
        label.setStyle("");
    }
}
