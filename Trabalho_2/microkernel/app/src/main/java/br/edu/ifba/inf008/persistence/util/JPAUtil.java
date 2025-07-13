package br.edu.ifba.inf008.persistence.util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
    private static final EntityManagerFactory FACTORY = buildEntityManagerFactory();
    
    private static EntityManagerFactory buildEntityManagerFactory() {
        try {
            return Persistence.createEntityManagerFactory("library-pu");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao criar EntityManagerFactory", e);
        }
    }
    
    public static EntityManagerFactory getEntityManagerFactory() {
        return FACTORY;
    }
    
    public static void shutdown() {
        if (FACTORY != null && FACTORY.isOpen()) {
            FACTORY.close();
        }
    }
}