package br.edu.ifba.inf008.interfaces;

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

    protected static ICore instance = null;
}
