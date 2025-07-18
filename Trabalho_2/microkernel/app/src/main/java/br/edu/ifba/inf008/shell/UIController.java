package br.edu.ifba.inf008.shell;

import br.edu.ifba.inf008.interfaces.IUIController;
import br.edu.ifba.inf008.interfaces.ICore;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import java.io.IOException;

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

            primaryStage.setScene(scene);
            primaryStage.show();

            // Inicializa os plugins
            Core.getInstance().getPluginController().init();
        } catch (IOException e) {
            System.err.println("Erro ao carregar o FXML: " + e.getMessage());
            e.printStackTrace();
            
            // Fallback para a interface programática original
            fallbackToOriginalUI(primaryStage);
        }
    }
    
    private void fallbackToOriginalUI(Stage primaryStage) {
        primaryStage.setTitle("Sistema de Biblioteca (Modo de Contingência)");

        menuBar = new MenuBar();
        tabPane = new TabPane();
        tabPane.setSide(Side.BOTTOM);

        VBox vBox = new VBox(menuBar, tabPane);

        Scene scene = new Scene(vBox, 960, 600);

        primaryStage.setScene(scene);
        primaryStage.show();

        Core.getInstance().getPluginController().init();
    }

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