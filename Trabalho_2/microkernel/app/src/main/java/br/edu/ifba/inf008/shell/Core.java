package br.edu.ifba.inf008.shell;

import br.edu.ifba.inf008.interfaces.IAuthenticationController;
import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IIOController;
import br.edu.ifba.inf008.interfaces.IPluginController;
import br.edu.ifba.inf008.interfaces.IUIController;
import br.edu.ifba.inf008.interfaces.persistence.IBookDAO;
import br.edu.ifba.inf008.interfaces.persistence.ILoanDAO;
import br.edu.ifba.inf008.interfaces.persistence.IUserDAO;
import br.edu.ifba.inf008.persistence.BookDAO;
import br.edu.ifba.inf008.persistence.LoanDAO;
import br.edu.ifba.inf008.persistence.UserDAO;

public class Core extends ICore
{
    private Core() {
        authenticationController = new AuthenticationController();
        ioController = new IOController();
        pluginController = new PluginController();
        
        userDAO = new UserDAO();
        bookDAO = new BookDAO();
        loanDAO = new LoanDAO();
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
    @Override
    public IUIController getUIController() {
        return UIController.getInstance();
    }
    @Override
    public IAuthenticationController getAuthenticationController() {
        return authenticationController;
    }
    @Override
    public IIOController getIOController() {
        return ioController;
    }
    @Override
    public IPluginController getPluginController() {
        return pluginController;
    }

    @Override
    public IUserDAO getUserDAO() {
        return userDAO;
    }

    @Override
    public IBookDAO getBookDAO() {
        return bookDAO;
    }

    @Override
    public ILoanDAO getLoanDAO() {
        return loanDAO;
    }

    private IAuthenticationController authenticationController = new AuthenticationController();
    private IIOController ioController = new IOController();
    private IPluginController pluginController = new PluginController();
    private IUserDAO userDAO;
    private IBookDAO bookDAO;
    private ILoanDAO loanDAO;
}
