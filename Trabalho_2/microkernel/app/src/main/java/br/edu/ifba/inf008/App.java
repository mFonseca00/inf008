package br.edu.ifba.inf008;

import br.edu.ifba.inf008.shell.Core;
import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.persistence.IUserDAO;
import br.edu.ifba.inf008.interfaces.persistence.IBookDAO;
import br.edu.ifba.inf008.interfaces.persistence.ILoanDAO;
import br.edu.ifba.inf008.interfaces.models.User;
import br.edu.ifba.inf008.interfaces.models.Book;
import br.edu.ifba.inf008.interfaces.models.Loan;
import java.util.List;
import java.time.LocalDate;

import br.edu.ifba.inf008.persistence.util.JPAUtil;

public class App {
    public static void main(String[] args) {
        try {
            // Inicializa o Core
            Core.init();
            
            // Testes para DAOs
            // FIXME: apagar no final
            // testUserDAO();
            // testBookDAO();
            // testLoanDAO();
            
        } finally {
            // Fecha recursos do Hibernate
            JPAUtil.shutdown();
        }
    }
    

    // FIXME: Apagar métodos de teste no DAO final
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
    
    private static void testBookDAO() {
        try {
            System.out.println("===== Testando BookDAO =====");
            
            // Obter referência ao BookDAO através do Core
            IBookDAO bookDAO = ICore.getInstance().getBookDAO();
            
            // Teste 1: Criar um livro
            Book newBook = new Book("Clean Code", "Robert C. Martin", "9780132350884", 2008, 1);
            System.out.println("Criando livro: " + newBook.getTitle());
            
            Book savedBook = bookDAO.save(newBook);
            System.out.println("Livro criado com ID: " + savedBook.getBookId());
            
            // Teste 2: Buscar por ID
            System.out.println("Buscando livro por ID: " + savedBook.getBookId());
            Book foundBook = bookDAO.findById(savedBook.getBookId()).orElse(null);
            System.out.println("Encontrado: " + (foundBook != null ? foundBook.getTitle() : "Não encontrado"));
            
            // Teste 3: Buscar por ISBN
            System.out.println("Buscando livro por ISBN: " + savedBook.getIsbn());
            Book bookByIsbn = bookDAO.findByIsbn(savedBook.getIsbn()).orElse(null);
            System.out.println("Encontrado por ISBN: " + (bookByIsbn != null ? bookByIsbn.getTitle() : "Não encontrado"));
            
            // Teste 4: Atualizar livro
            System.out.println("Atualizando livro...");
            foundBook.setTitle("Clean Code: A Handbook of Agile Software Craftsmanship");
            Book updatedBook = bookDAO.update(foundBook);
            System.out.println("Livro atualizado: " + updatedBook.getTitle());
            
            // Teste 5: Listar todos
            System.out.println("Listando todos os livros:");
            List<Book> allBooks = bookDAO.findAll();
            for (Book book : allBooks) {
                System.out.println(" - " + book.getTitle() + " por " + book.getAuthor());
            }
            
            // Teste 6: Buscar por autor
            System.out.println("Buscando livros do autor 'Martin':");
            List<Book> booksByAuthor = bookDAO.findByAuthor("Martin");
            for (Book book : booksByAuthor) {
                System.out.println(" - " + book.getTitle() + " por " + book.getAuthor());
            }
            
            // Teste 7: Buscar por ano de publicação
            System.out.println("Buscando livros publicados em 2008:");
            List<Book> booksByYear = bookDAO.findByPublishedYear(2008);
            for (Book book : booksByYear) {
                System.out.println(" - " + book.getTitle() + " (" + book.getPublishedYear() + ")");
            }
            
            // Teste 8: Excluir o livro
            System.out.println("Excluindo livro com ID: " + savedBook.getBookId());
            boolean deleted = bookDAO.delete(savedBook.getBookId());
            System.out.println("Livro excluído: " + deleted);
            
            System.out.println("===== Teste concluído =====");
        } catch (Exception e) {
            System.out.println("ERRO no teste: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void testLoanDAO() {
        try {
            System.out.println("===== Testando LoanDAO =====");
            
            // Obter referências aos DAOs através do Core
            ILoanDAO loanDAO = ICore.getInstance().getLoanDAO();
            IUserDAO userDAO = ICore.getInstance().getUserDAO();
            IBookDAO bookDAO = ICore.getInstance().getBookDAO();
            
            // Preparar dados para o teste
            // Criar um usuário
            String uniqueEmail = "teste" + System.currentTimeMillis() + "@app.com";
            User user = new User("Teste Loan", uniqueEmail);
            User savedUser = userDAO.save(user);
            System.out.println("Usuário criado com ID: " + savedUser.getUserId());
            
            // Criar um livro
            Book book = new Book("Java Programming", "John Doe", "9781234567890", 2020, 2);
            Book savedBook = bookDAO.save(book);
            System.out.println("Livro criado com ID: " + savedBook.getBookId());
            
            // Teste 1: Criar um empréstimo
            LocalDate now = LocalDate.now();
            LocalDate dueDate = now.plusDays(14); // empréstimo por 14 dias
            
            Loan newLoan = new Loan(savedBook, savedUser, now, dueDate);
            System.out.println("Criando empréstimo para usuário " + savedUser.getName());
            
            Loan savedLoan = loanDAO.save(newLoan);
            System.out.println("Empréstimo criado com ID: " + savedLoan.getLoanId());
            
            // Teste 2: Buscar por ID
            System.out.println("Buscando empréstimo por ID: " + savedLoan.getLoanId());
            Loan foundLoan = loanDAO.findById(savedLoan.getLoanId()).orElse(null);
            System.out.println("Encontrado: " + (foundLoan != null ? "Sim" : "Não"));
            
            // Teste 3: Buscar por ID do usuário
            System.out.println("Buscando empréstimos do usuário ID: " + savedUser.getUserId());
            List<Loan> userLoans = loanDAO.findByUserId(savedUser.getUserId());
            System.out.println("Empréstimos encontrados: " + userLoans.size());
            
            // Teste 4: Buscar por ID do livro
            System.out.println("Buscando empréstimos do livro ID: " + savedBook.getBookId());
            List<Loan> bookLoans = loanDAO.findByBookId(savedBook.getBookId());
            System.out.println("Empréstimos encontrados: " + bookLoans.size());
            
            // Teste 5: Atualizar empréstimo (devolver o livro)
            System.out.println("Atualizando empréstimo (devolvendo o livro)...");
            foundLoan.setReturnDate(LocalDate.now());
            Loan updatedLoan = loanDAO.update(foundLoan);
            System.out.println("Empréstimo atualizado, devolvido em: " + updatedLoan.getReturnDate());
            
            // Teste 6: Listar todos os empréstimos
            System.out.println("Listando todos os empréstimos:");
            List<Loan> allLoans = loanDAO.findAll();
            for (Loan loan : allLoans) {
                System.out.println(" - Empréstimo ID: " + loan.getLoanId() + 
                                   ", Livro: " + loan.getBook().getTitle() + 
                                   ", Usuário: " + loan.getUser().getName());
            }
            
            // Teste 7: Excluir o empréstimo, o usuário e o livro
            System.out.println("Excluindo empréstimo com ID: " + savedLoan.getLoanId());
            boolean loanDeleted = loanDAO.delete(savedLoan.getLoanId());
            System.out.println("Empréstimo excluído: " + loanDeleted);
            
            System.out.println("Excluindo usuário com ID: " + savedUser.getUserId());
            boolean userDeleted = userDAO.delete(savedUser.getUserId());
            System.out.println("Usuário excluído: " + userDeleted);
            
            System.out.println("Excluindo livro com ID: " + savedBook.getBookId());
            boolean bookDeleted = bookDAO.delete(savedBook.getBookId());
            System.out.println("Livro excluído: " + bookDeleted);
            
            System.out.println("===== Teste concluído =====");
        } catch (Exception e) {
            System.out.println("ERRO no teste: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
