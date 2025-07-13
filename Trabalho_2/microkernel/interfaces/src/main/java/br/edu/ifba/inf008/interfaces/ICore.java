package br.edu.ifba.inf008.interfaces;

import br.edu.ifba.inf008.interfaces.persistence.IBookDAO;
import br.edu.ifba.inf008.interfaces.persistence.ILoanDAO;
import br.edu.ifba.inf008.interfaces.persistence.IUserDAO;

public abstract class ICore
{
    public static ICore getInstance() {
        return instance;
    }

    public abstract IUIController getUIController();
    public abstract IAuthenticationController getAuthenticationController();
    public abstract IIOController getIOController();
    public abstract IPluginController getPluginController();

    public abstract IUserDAO getUserDAO();
    public abstract IBookDAO getBookDAO();
    public abstract ILoanDAO getLoanDAO();

    protected static ICore instance = null;
}
