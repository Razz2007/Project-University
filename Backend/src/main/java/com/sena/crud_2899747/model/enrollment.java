package com.sena.crud_2899747.model;

import java.sql.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity(name = "enrollment")
public class enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_id")
    private int enrollmentId;

    @Column(name = "enrollment_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date enrollmentDate;

    @Column(name = "semester", length = 20, nullable = false)
    private String semester;

    @Column(name = "grade", precision = 5, nullable = true)
    private Double grade;

    @Column(name = "status_Enrollment", length = 20, nullable = false)
    private String statusEnrollment;

    @Column(name = "status", nullable = false, columnDefinition = "boolean default true")
    private boolean status;

    // Constructor vacío        
    public enrollment() {
    }

    public enrollment(int enrollmentId, Date enrollmentDate, String semester, Double grade, String statusEnrollment, boolean status) {
        this.enrollmentId = enrollmentId;
        this.enrollmentDate = enrollmentDate;
        this.semester = semester;
        this.grade = grade;
        this.statusEnrollment = statusEnrollment;
        this.status = status;
    }

    // Getters y Setters
    public int getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(int enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public Date getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(Date enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public String getStatusEnrollment() {
        return statusEnrollment;
    }

    public void setStatusEnrollment(String statusEnrollment) {
        this.statusEnrollment = statusEnrollment;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
