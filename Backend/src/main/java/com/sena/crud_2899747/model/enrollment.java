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

@Entity(name = "enrollment")
public class enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_id")
    private int enrollmentId;

      @ManyToOne
    @JoinColumn(name = "student_id")
    private student student; 


    @ManyToOne
    @JoinColumn(name = "course_id")
    private course course; 


    @Column(name = "enrollment_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date enrollmentDate;

    @Column(name = "semester", length = 20, nullable = false)
    private String semester;

    @Column(name = "grade", precision = 5, nullable = true)
    private Double grade;

    @Column(name = "status", length = 20, nullable = false)
    private String status;

    public enrollment(int enrollmentId, int studentId, int courseId, Date enrollmentDate, String semester, Double grade, String status) {
        this.enrollmentId = enrollmentId;
        this.enrollmentDate = enrollmentDate;
        this.semester = semester;
        this.grade = grade;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
