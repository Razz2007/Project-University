package com.sena.crud_2899747.DTO;

import java.sql.Date;

public class enrollmentDTO {
    private int enrollmentId;
    private Date enrollmentDate;
    private String semester;
    private Double grade;
    private String statusEnrollment;
    private boolean status;

    // Constructor vacío
    public enrollmentDTO() {
    }

    // Constructor con parámetros
    public enrollmentDTO(int enrollmentId, Date enrollmentDate, String semester, Double grade, String statusEnrollment, boolean status) {
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
