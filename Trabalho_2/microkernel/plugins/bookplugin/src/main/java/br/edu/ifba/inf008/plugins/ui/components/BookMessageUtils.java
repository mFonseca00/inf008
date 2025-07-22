package br.edu.ifba.inf008.plugins.ui.components;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

public class BookMessageUtils {

    public static void displayErrorMessage(Label label, String message) {
        clearMessageStyles(label);
        label.getStyleClass().add("message-error");
        label.setText(message);
    }
    
    public static void displaySuccessMessage(Label label, String message) {
        clearMessageStyles(label);
        label.getStyleClass().add("message-success");
        label.setText(message);
    }
    
    public static void displayConfirmationMessage(Label label, String message) {
        clearMessageStyles(label);
        label.getStyleClass().add("message-warning");
        label.setText(message);
    }
    
    public static void clearMessage(Label label) {
        label.setText("");
        clearMessageStyles(label);
        label.getStyleClass().add("message-info");
    }
    
    private static void clearMessageStyles(Label label) {
        label.getStyleClass().removeAll("message-error", "message-success", "message-warning", "message-info");
    }

    public static boolean showConfirmation(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação");
        alert.setHeaderText("Confirmação necessária");
        alert.setContentText(message);
        
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
        return result == ButtonType.OK;
    }
}
