package com.sena.crud_2899747.DTO;

import java.sql.Date;
import com.sena.crud_2899747.model.book;
import com.sena.crud_2899747.model.student;

public class loanDTO {
    private int loanId;
    private student student;
    private book book;
    private Date loanDate;
    private Date returnDate;
    private boolean status;

    // Constructor vacío
    public loanDTO() {
    }

    // Constructor con parámetros
    public loanDTO(int loanId, student student, book book, Date loanDate, Date returnDate, boolean status) {
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
