package br.edu.ifba.inf008.plugins.ui;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class LoanUIUtils {
    
    public static Node createErrorContainer(String title, String message, String details) {
        VBox container = new VBox(10);
        container.getStyleClass().add("error-container");
        
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("error-title");
        
        Label messageLabel = new Label(message);
        messageLabel.getStyleClass().add("error-message");
        
        container.getChildren().addAll(titleLabel, messageLabel);
        
        if (details != null && !details.trim().isEmpty()) {
            Label detailsLabel = new Label("Detalhes: " + details);
            detailsLabel.getStyleClass().add("error-detail");
            container.getChildren().add(detailsLabel);
        }
        
        return container;
    }
    
    public static void showMessage(Label messageLabel, String message, boolean isError) {
        if (messageLabel != null) {
            messageLabel.setText(message);
            messageLabel.getStyleClass().clear();
            if (isError) {
                messageLabel.getStyleClass().add("message-error");
            } else {
                messageLabel.getStyleClass().add("message-success");
            }
        }
    }
    
    public static void clearMessage(Label messageLabel) {
        if (messageLabel != null) {
            messageLabel.setText("");
            messageLabel.getStyleClass().clear();
            messageLabel.getStyleClass().add("message-info");
        }
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