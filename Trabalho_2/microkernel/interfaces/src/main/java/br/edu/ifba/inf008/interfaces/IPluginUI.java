package br.edu.ifba.inf008.interfaces;

import javafx.scene.Node;

public interface IPluginUI extends IPlugin {
    
    String getMenuCategory();
    String getMenuItemName();
    String getTabTitle();
    Node createTabContent();
}