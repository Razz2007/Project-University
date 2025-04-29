package com.sena.crud_2899747.DTO;

import com.sena.crud_2899747.model.degree;

public class studentDTO {
    private int studentId;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String email;
    private String phone;
    private String address;
    private degree degreeId;

    // Constructor con parámetros
    public studentDTO(int studentId, String firstName, String lastName, String birthDate, String email, String phone,
                      String address, degree degreeId) {
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

    public degree getDegreeId() {
        return degreeId;
    }

    public void setDegreeId(degree degreeId) {
        this.degreeId = degreeId;
    }
}
