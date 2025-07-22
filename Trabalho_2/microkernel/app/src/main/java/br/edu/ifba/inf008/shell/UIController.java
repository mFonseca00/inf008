package br.edu.ifba.inf008.shell;

import java.io.IOException;

import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IUIController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UIController extends Application implements IUIController
{
    private ICore core;
    private static UIController uiController;
    
    @FXML
    private MenuBar menuBar;
    
    @FXML
    private TabPane tabPane;

    public UIController() {
    }

    @Override
    public void init() {
        uiController = this;
    }

    public static UIController getInstance() {
        return uiController;
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            primaryStage.setTitle("Alexandria");

            // Carrega o FXML da tela principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
            
            // Define este controlador como o controlador do FXML
            loader.setController(this);
            
            // Carrega a raiz do FXML
            VBox root = loader.load();
            
            Scene scene = new Scene(root, 960, 600);
            
            // Aplica estilos básicos diretamente
            applyThemeToScene(scene);

            primaryStage.setScene(scene);

            primaryStage.setMinWidth(800);
            primaryStage.setMinHeight(500);
            
            // Maximizar a janela
            primaryStage.setMaximized(true);

            primaryStage.show();

            // Inicializa os plugins
            Core.getInstance().getPluginController().init();
        } catch (IOException e) {
            System.err.println("Erro ao carregar o FXML: " + e.getMessage());
            e.printStackTrace();
            
            // Fallback para a interface programática original
            fallbackToOriginalUIWithCSS(primaryStage);
        }
    }
    
    private void fallbackToOriginalUIWithCSS(Stage primaryStage) {
        primaryStage.setTitle("Alexandria - Sistema de Biblioteca");

        // Criar menu bar com menu Geral
        menuBar = new MenuBar();
        Menu geralMenu = new Menu("Geral");
        MenuItem sairItem = new MenuItem("Sair");
        sairItem.setOnAction(e -> handleExit());
        geralMenu.getItems().add(sairItem);
        menuBar.getMenus().add(geralMenu);
        
        tabPane = new TabPane();
        tabPane.setSide(Side.BOTTOM);
        
        // Criar tab inicial
        Tab inicioTab = new Tab("Início");
        inicioTab.setClosable(false);
        VBox welcomeContent = new VBox();
        welcomeContent.setAlignment(Pos.CENTER);
        welcomeContent.setSpacing(40);
        welcomeContent.setStyle("-fx-padding: 60; -fx-background-color: #fafafa;");
        
        Label titleLabel = new Label("Alexandria");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: 300; -fx-text-fill: #333333;");
        Label subtitleLabel = new Label("Sistema de Biblioteca");
        subtitleLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #666666; -fx-font-weight: 300;");
        
        VBox descContainer = new VBox();
        descContainer.setAlignment(Pos.CENTER);
        descContainer.setSpacing(12);
        descContainer.setStyle("-fx-max-width: 400;");
        
        Label desc1 = new Label("Utilize o menu superior para acessar as funcionalidades");
        desc1.setStyle("-fx-font-size: 13px; -fx-text-fill: #888888; -fx-text-alignment: center; -fx-wrap-text: true;");
        Label desc2 = new Label("Instale plugins para habilitar recursos adicionais");
        desc2.setStyle("-fx-font-size: 13px; -fx-text-fill: #888888; -fx-text-alignment: center; -fx-wrap-text: true;");
        
        descContainer.getChildren().addAll(desc1, desc2);
        welcomeContent.getChildren().addAll(titleLabel, subtitleLabel, descContainer);
        inicioTab.setContent(welcomeContent);
        tabPane.getTabs().add(inicioTab);

        VBox vBox = new VBox(menuBar, tabPane);
        vBox.setStyle("-fx-background-color: #fafafa; -fx-font-family: 'Segoe UI', 'San Francisco', 'Helvetica Neue', sans-serif;");
        
        // Aplica estilos minimalistas aos componentes
        menuBar.setStyle("-fx-background-color: #ffffff; -fx-border-color: #e0e0e0; -fx-border-width: 0 0 1 0; -fx-padding: 8 16;");
        tabPane.setStyle("-fx-background-color: #fafafa; -fx-tab-min-width: 100px; -fx-tab-max-width: 160px;");

        Scene scene = new Scene(vBox, 960, 600);
        
        // Tentar aplicar o CSS mesmo no fallback
        try {
            scene.getStylesheets().add(getClass().getResource("/styles/alexandria-theme.css").toExternalForm());
        } catch (Exception cssError) {
            System.err.println("Não foi possível carregar o CSS: " + cssError.getMessage());
        }

        primaryStage.setScene(scene);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(500);
        
        // Maximizar a janela
        primaryStage.setMaximized(true);
        
        primaryStage.show();

        Core.getInstance().getPluginController().init();
    }
    
    private void applyThemeToScene(Scene scene) {
        // Tenta aplicar CSS do arquivo primeiro
        try {
            String cssPath = getClass().getResource("/styles/alexandria-theme.css").toExternalForm();
            scene.getStylesheets().add(cssPath);
        } catch (Exception e) {
            System.out.println("CSS arquivo não encontrado, aplicando estilos inline básicos");
            // Aplica estilos básicos inline
            applyInlineStyles(scene);
        }
    }
    
    private void applyInlineStyles(Scene scene) {
        // Aplica tema minimalista
        VBox root = (VBox) scene.getRoot();
        root.setStyle("-fx-background-color: #fafafa; -fx-font-family: 'Segoe UI', 'San Francisco', 'Helvetica Neue', sans-serif;");
        
        // Aplica estilo minimalista ao MenuBar
        if (menuBar != null) {
            menuBar.setStyle("-fx-background-color: #ffffff; -fx-border-color: #e0e0e0; -fx-border-width: 0 0 1 0; -fx-padding: 8 16;");
        }
        
        // Aplica estilo minimalista ao TabPane
        if (tabPane != null) {
            tabPane.setStyle("-fx-background-color: #fafafa; -fx-tab-min-width: 100px; -fx-tab-max-width: 160px;");
        }
    }

    @Override
    public MenuItem createMenuItem(String menuText, String menuItemText) {
        // Criar o menu caso ele não exista
        Menu newMenu = null;
        for (Menu menu : menuBar.getMenus()) {
            if (menu.getText().equals(menuText)) {
                newMenu = menu;
                break;
            }
        }
        if (newMenu == null) {
            newMenu = new Menu(menuText);
            menuBar.getMenus().add(newMenu);
        }

        // Criar o menu item neste menu
        MenuItem menuItem = new MenuItem(menuItemText);
        newMenu.getItems().add(menuItem);

        return menuItem;
    }

    @Override
    public boolean createTab(String tabText, Node contents) {
        Tab tab = new Tab();
        tab.setText(tabText);
        tab.setContent(contents);
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);

        return true;
    }
    
    @FXML
    private void handleExit() {
        System.exit(0);
    }
}