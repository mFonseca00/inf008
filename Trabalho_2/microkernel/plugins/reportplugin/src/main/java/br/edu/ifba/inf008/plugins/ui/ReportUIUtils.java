package br.edu.ifba.inf008.plugins.ui;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class ReportUIUtils {
    
    /**
     * Cria um container de erro para exibir quando algo der errado
     */
    public static Node createErrorContainer(String title, String message, String details) {
        VBox container = new VBox(10);
        container.setPadding(new Insets(20));
        
        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("System Bold", 16));
        titleLabel.setStyle("-fx-text-fill: #d32f2f;");
        
        Label messageLabel = new Label(message);
        messageLabel.setWrapText(true);
        messageLabel.setStyle("-fx-text-fill: #666666;");
        
        container.getChildren().addAll(titleLabel, messageLabel);
        
        if (details != null && !details.trim().isEmpty()) {
            Label detailsLabel = new Label("Detalhes: " + details);
            detailsLabel.setWrapText(true);
            detailsLabel.setStyle("-fx-text-fill: #999999; -fx-font-size: 11px;");
            container.getChildren().add(detailsLabel);
        }
        
        return container;
    }
}