package br.edu.ifba.inf008.shell;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifba.inf008.App;
import br.edu.ifba.inf008.interfaces.IPlugin;
import br.edu.ifba.inf008.interfaces.IPluginController;
import br.edu.ifba.inf008.interfaces.IPluginUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;

public class PluginController implements IPluginController
{
    private List<IPlugin> loadedPlugins = new ArrayList<>();
    
    @Override
    public boolean init() {
        try {
            File currentDir = new File("./plugins");
            
            if (!currentDir.exists()) {
                currentDir.mkdir();
                System.out.println("Diretório de plugins criado: " + currentDir.getAbsolutePath());
                return true;
            }

            FilenameFilter jarFilter = (dir, name) -> name.toLowerCase().endsWith(".jar");

            String[] plugins = currentDir.list(jarFilter);
            
            if (plugins == null || plugins.length == 0) {
                System.out.println("Nenhum plugin encontrado em " + currentDir.getAbsolutePath());
                return true;
            }
            
            System.out.println("Encontrados " + plugins.length + " plugins para carregar");
            
            URL[] jars = new URL[plugins.length];
            for (int i = 0; i < plugins.length; i++) {
                jars[i] = new File("./plugins/" + plugins[i]).toURL();
            }
            
            URLClassLoader ulc = new URLClassLoader(jars, App.class.getClassLoader());
            
            for (String pluginJar : plugins) {
                try {
                    String pluginName = pluginJar.split("\\.")[0];
                    System.out.println("Carregando plugin: " + pluginName);
                    
                    IPlugin plugin = (IPlugin) Class.forName("br.edu.ifba.inf008.plugins." + pluginName, true, ulc).newInstance();
                    loadedPlugins.add(plugin);
                    
                    boolean success = plugin.init();
                    if (!success) {
                        System.out.println("Falha na inicialização do plugin: " + pluginName);
                        continue;
                    }
                    
                    if (plugin instanceof IPluginUI) {
                        setupPluginUI((IPluginUI) plugin);
                    }
                } catch (Exception e) {
                    System.out.println("Erro ao carregar plugin " + pluginJar + ": " + e.getMessage());
                    e.printStackTrace();
                }
            }

            return true;
        } catch (Exception e) {
            System.out.println("Erro no carregamento de plugins: " + e.getClass().getName() + " - " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    private void setupPluginUI(IPluginUI plugin) {
        UIController uiController = UIController.getInstance();
        
        if (plugin.getMenuCategory() != null && plugin.getMenuItemName() != null) {
            MenuItem menuItem = uiController.createMenuItem(plugin.getMenuCategory(), plugin.getMenuItemName());
            
            menuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (plugin.getTabTitle() != null) {
                        uiController.createTab(plugin.getTabTitle(), plugin.createTabContent());
                    }
                }
            });
        }
    }
    
    public List<IPlugin> getLoadedPlugins() {
        return loadedPlugins;
    }
}