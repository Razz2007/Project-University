package com.sena.crud_2899747.DTO;

import java.math.BigDecimal;
import java.sql.Date;
import com.sena.crud_2899747.model.department;

public class professorDTO {

    private int professorId;  // ID único del profesor
    private String firstName;
    private String lastName;
    private String specialty;
    private Date hireDate;
    private String email;
    private String phone;
    private BigDecimal salary;
    private department departmentId;  

    // Constructor vacío
    public professorDTO() {
    }

    // Constructor con parámetros
    public professorDTO(int professorId, String firstName, String lastName, String specialty, Date hireDate, 
                        String email, String phone, BigDecimal salary, department departmentId) {
        this.professorId = professorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialty = specialty;
        this.hireDate = hireDate;
        this.email = email;
        this.phone = phone;
        this.salary = salary;
        this.departmentId = departmentId;
    }

    // Getters y Setters
    public int getProfessorId() {
        return professorId;
    }

    public void setProfessorId(int professorId) {
        this.professorId = professorId;
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

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
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

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public department getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(department departmentId) {
        this.departmentId = departmentId;
    }
}
