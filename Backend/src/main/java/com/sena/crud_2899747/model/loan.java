package com.sena.crud_2899747.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

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

    @Column(name = "status", length = 20, nullable = false)
    private String status;

    public loan(int loanId, Date loanDate, Date returnDate, String status) {
        this.loanId = loanId;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
