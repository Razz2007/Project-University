package com.sena.crud_2899747.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "student")
public class student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private int studentId;

    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    @Column(name = "birth_date", nullable = false)
    private String birthDate;

    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @Column(name = "phone", length = 20, nullable = false)
    private String phone;

    @Column(name = "address", length = 200, nullable = false)
    private String address;

    @ManyToOne
    @JoinColumn(name = "degree_id", nullable = false)
    private degree degreeId;

    @Column(name = "status", nullable = false, columnDefinition = "boolean default true")
    private boolean status;


    // Constructor vacío
    public student() {
    }

    // Constructor con parámetros
    public student(int studentId, String firstName, String lastName, String birthDate,
            String email, String phone, String address, degree degreeId, boolean status) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.degreeId = degreeId;
    }

    // Getters y setters
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public com.sena.crud_2899747.model.degree getDegreeId() {
        return degreeId;
    }

    public void setDegreeId(degree degreeId) {
        this.degreeId = degreeId;
    }
    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
