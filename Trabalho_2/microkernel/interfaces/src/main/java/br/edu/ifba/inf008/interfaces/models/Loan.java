package br.edu.ifba.inf008.interfaces.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_id")
    private Integer loanId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "loan_date")
    private LocalDate loanDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    // Construtores
    public Loan() {}

    public Loan(Integer loanId, Book book, User user, LocalDate loanDate, LocalDate returnDate) {
        this.loanId = loanId;
        this.book = book;
        this.user = user;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }

    public Loan(Book book, User user, LocalDate loanDate, LocalDate returnDate) {
        this.book = book;
        this.user = user;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }

    // Getters e Setters
    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    // toString

    @Override
    public String toString() {
        return "Loan [loanId=" + loanId + ", book=" + book.getTitle() + 
               ", user=" + user.getName() + ", loanDate=" + loanDate + 
               ", returnDate=" + returnDate + "]";
    }
}
