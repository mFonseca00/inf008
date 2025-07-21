package br.edu.ifba.inf008.plugins.ui.components;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
    
    /**
     * Mostra um dialog de confirmação para o usuário
     * @param message Mensagem a ser exibida
     * @return true se o usuário confirmar, false caso contrário
     */
    public static boolean showConfirmation(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação");
        alert.setHeaderText("Confirmação necessária");
        alert.setContentText(message);
        
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
        return result == ButtonType.OK;
    }
}
