package br.edu.ifba.inf008.interfaces;

import javafx.scene.Node;

// FIXME: Remover comentários ou mudar para português
public interface IPluginUI extends IPlugin {
    
    /**
     * Retorna o nome da categoria de menu que este plugin usa
     */
    String getMenuCategory();
    
    /**
     * Retorna o nome do item de menu que este plugin adiciona
     */
    String getMenuItemName();
    
    /**
     * Retorna o título da tab que este plugin criará
     */
    String getTabTitle();
    
    /**
     * Cria o conteúdo da tab deste plugin
     */
    Node createTabContent();
}