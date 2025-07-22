package br.edu.ifba.inf008.plugins.ui;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ReportUIUtils {
    
    /**
     * Cria um container de erro para exibir quando algo der errado
     */
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
}