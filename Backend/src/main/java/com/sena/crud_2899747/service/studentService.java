package com.sena.crud_2899747.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sena.crud_2899747.DTO.studentDTO;
import com.sena.crud_2899747.model.student;
import com.sena.crud_2899747.repository.Istudent;


@Service
public class studentService {
    /*
     * save
     * findAll
     * findById
     * Delete
     */
    /* establish connection with the interface */
    @Autowired
    private Istudent data;

    // register and update
    public void save(studentDTO studentDTO) {
        student studentRegister = convertToModel(studentDTO);
        data.save(studentRegister);
    }

    public studentDTO convertToDTO(student student) {
        studentDTO studentdto = new studentDTO(
                student.getFirstName(),
                student.getLastName(),
                student.getBirthDate(),
                student.getEmail(),
                student.getPhone(),
                student.getAddress(),
                student.getDegreeId());
        return studentdto;
    }

    public student convertToModel(studentDTO studentDTO) {
        student student = new student(
                0,  
                studentDTO.getFirstName(),
                studentDTO.getLastName(),
                studentDTO.getBirthDate(),
                studentDTO.getEmail(),
                studentDTO.getPhone(),
                studentDTO.getAddress(),
                studentDTO.getDegreeId());
        return student;
    }
}