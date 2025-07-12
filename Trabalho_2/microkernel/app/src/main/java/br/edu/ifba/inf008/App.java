package br.edu.ifba.inf008;

import br.edu.ifba.inf008.shell.Core;
import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.persistence.IUserDAO;
import br.edu.ifba.inf008.interfaces.models.User;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        // Inicializa o Core
        Core.init();
        
        // Testam os DAOs
        // testUserDAO();
    }
    
    private static void testUserDAO() {
        try {
            System.out.println("===== Testando UserDAO =====");
            
            // Obter referência ao UserDAO através do Core
            IUserDAO userDAO = ICore.getInstance().getUserDAO();
            
            // Teste 1: Criar um usuário
            User newUser = new User("Teste App", "teste@app.com");
            System.out.println("Criando usuário: " + newUser.getName());
            
            User savedUser = userDAO.save(newUser);
            System.out.println("Usuário criado com ID: " + savedUser.getUserId());
            
            // Teste 2: Buscar por ID
            System.out.println("Buscando usuário por ID: " + savedUser.getUserId());
            User foundUser = userDAO.findById(savedUser.getUserId()).orElse(null);
            System.out.println("Encontrado: " + (foundUser != null ? foundUser : "Não encontrado"));
            
            // Teste 3: Atualizar usuário
            System.out.println("Atualizando usuário...");
            foundUser.setName("Teste Atualizado");
            User updatedUser = userDAO.update(foundUser);
            System.out.println("Usuário atualizado: " + updatedUser.getName());
            
            // Teste 4: Listar todos
            System.out.println("Listando todos os usuários:");
            List<User> allUsers = userDAO.findAll();
            for (User user : allUsers) {
                System.out.println(" - " + user);
            }
            
            // Teste 5: Buscar por nome
            System.out.println("Buscando usuários com nome 'Teste':");
            List<User> usersWithName = userDAO.findByName("Teste");
            for (User user : usersWithName) {
                System.out.println(" - " + user);
            }
            
            // Teste 6: Buscar por email
            System.out.println("Buscando usuário com email 'teste@app.com':");
            User userWithEmail = userDAO.findByEmail("teste@app.com").orElse(null);
            System.out.println("Encontrado: " + (userWithEmail != null ? userWithEmail : "Não encontrado"));
            
            // Teste 7: Excluir o usuário (descomente se quiser testar)
            
            System.out.println("Excluindo usuário com ID: " + savedUser.getUserId());
            boolean deleted = userDAO.delete(savedUser.getUserId());
            System.out.println("Usuário excluído: " + deleted);
            
            
            System.out.println("===== Teste concluído =====");
        } catch (Exception e) {
            System.out.println("ERRO no teste: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
