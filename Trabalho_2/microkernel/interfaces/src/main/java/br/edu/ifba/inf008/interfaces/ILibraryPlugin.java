package br.edu.ifba.inf008.interfaces;

// FIXME: Remover comentários ou mudar para português
public interface ILibraryPlugin extends IPlugin {
    
    /**
     * Retorna o tipo do plugin de biblioteca (ex: "book", "user", "loan")
     */
    String getLibraryFeatureType();
}