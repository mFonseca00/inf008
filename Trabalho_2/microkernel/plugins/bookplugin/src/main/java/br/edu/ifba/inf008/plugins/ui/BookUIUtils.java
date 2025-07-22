package br.edu.ifba.inf008.plugins.ui;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class BookUIUtils {

    public static VBox createErrorContainer(String errorTitle, String errorMessage, String errorDetail) {
        VBox errorContainer = new VBox(15);
        errorContainer.getStyleClass().add("error-container");
        
        Label titleLabel = new Label(errorTitle);
        titleLabel.getStyleClass().add("error-title");
        
        Label messageLabel = new Label(errorMessage);
        Label detailLabel = new Label("Detalhes: " + errorDetail);
        detailLabel.getStyleClass().add("error-detail");
        
        Label contactAdmin = new Label("Entre em contato com o administrador do sistema.");
        
        errorContainer.getChildren().addAll(
            titleLabel,
            new Label(""),  
            messageLabel,
            detailLabel,
            new Label(""),  
            contactAdmin
        );
        
        return errorContainer;
    }
    
    public static void showMessage(Label messageLabel, String message, boolean isError) {
        messageLabel.setText(message);
        messageLabel.getStyleClass().removeAll("message-error", "message-success");
        messageLabel.getStyleClass().add(isError ? "message-error" : "message-success");
    }
    
    public static void clearMessage(Label messageLabel) {
        messageLabel.setText("");
        messageLabel.getStyleClass().removeAll("message-error", "message-success");
        messageLabel.getStyleClass().add("message-info");
    }
}
