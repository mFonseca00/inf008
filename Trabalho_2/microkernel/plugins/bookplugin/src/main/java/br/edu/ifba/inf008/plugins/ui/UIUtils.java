package br.edu.ifba.inf008.plugins.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class UIUtils {

    public static VBox createErrorContainer(String errorTitle, String errorMessage, String errorDetail) {
        VBox errorContainer = new VBox(15);
        errorContainer.setPadding(new Insets(30));
        errorContainer.setAlignment(Pos.CENTER);
        
        Label titleLabel = new Label(errorTitle);
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #e74c3c;");
        
        Label messageLabel = new Label(errorMessage);
        Label detailLabel = new Label("Detalhes: " + errorDetail);
        detailLabel.setStyle("-fx-font-style: italic;");
        
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
}
