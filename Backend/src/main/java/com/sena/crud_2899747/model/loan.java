package com.sena.crud_2899747.model;

import java.sql.Date;
import jakarta.persistence.*;

@Entity(name = "loan")
public class loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_id")
    private int loanId;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private student student; 

    @ManyToOne
    @JoinColumn(name = "book_id")
    private book book; 

    @Column(name = "loan_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date loanDate;

    @Column(name = "return_date", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date returnDate;

    @Column(name = "status", nullable = false, columnDefinition = "boolean default true")
    private boolean status;

    // Constructor vacío
    public loan() {
    }

    // Constructor con parámetros
    public loan(int loanId, student student, book book, Date loanDate, Date returnDate, boolean status) {
        this.loanId = loanId;
        this.student = student;
        this.book = book;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    // Getters y Setters
    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public student getStudent() {
        return student;
    }

    public void setStudent(student student) {
        this.student = student;
    }

    public book getBook() {
        return book;
    }

    public void setBook(book book) {
        this.book = book;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
