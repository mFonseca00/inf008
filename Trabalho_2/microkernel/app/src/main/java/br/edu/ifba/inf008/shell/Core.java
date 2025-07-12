package br.edu.ifba.inf008.shell;

import br.edu.ifba.inf008.interfaces.*;
import br.edu.ifba.inf008.interfaces.persistence.IUserDAO;
import br.edu.ifba.inf008.persistence.UserDAO;
import javafx.application.Application;
import javafx.application.Platform;

public class Core extends ICore
{
    private Core() {
        // Inicializa os controladores aqui
        authenticationController = new AuthenticationController();
        ioController = new IOController();
        pluginController = new PluginController();
        
        // Inicializa os DAOs
        userDAO = new UserDAO();
    }

    public static boolean init() {
        if (instance != null) {
            System.out.println("Fatal error: core is already initialized!");
            System.exit(-1);
        }

	    instance = new Core();
        UIController.launch(UIController.class);

        return true;
    }
    public IUIController getUIController() {
        return UIController.getInstance();
    }
    public IAuthenticationController getAuthenticationController() {
        return authenticationController;
    }
    public IIOController getIOController() {
        return ioController;
    }
    public IPluginController getPluginController() {
        return pluginController;
    }

    @Override
    public IUserDAO getUserDAO() {
        return userDAO;
    }

    private IAuthenticationController authenticationController = new AuthenticationController();
    private IIOController ioController = new IOController();
    private IPluginController pluginController = new PluginController();
    private IUserDAO userDAO;
}
