package br.edu.ifba.inf008.plugins.ui;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class UIUtils {
    
    public static Node createErrorContainer(String title, String message, String details) {
        VBox errorContainer = new VBox(10);
        errorContainer.setPadding(new Insets(20));
        
        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
        titleLabel.setTextFill(Color.RED);
        
        Label messageLabel = new Label(message);
        messageLabel.setFont(Font.font("System", 14));
        
        Label detailsLabel = new Label("Detalhes: " + details);
        detailsLabel.setFont(Font.font("System", 12));
        detailsLabel.setWrapText(true);
        
        errorContainer.getChildren().addAll(titleLabel, messageLabel, detailsLabel);
        
        return errorContainer;
    }
    
    public static void showMessage(Label messageLabel, String message, boolean isError) {
        messageLabel.setText(message);
        messageLabel.setTextFill(isError ? Color.RED : Color.GREEN);
    }
    
    public static void clearMessage(Label messageLabel) {
        messageLabel.setText("");
    }
}
