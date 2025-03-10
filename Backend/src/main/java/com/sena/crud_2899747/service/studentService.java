package com.sena.crud_2899747.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sena.crud_2899747.DTO.studentDTO;
import com.sena.crud_2899747.model.student;
import com.sena.crud_2899747.repository.Istudent;


@Service
public class studentService {

    @Autowired
    private Istudent studentRepository;

    // Register and update
    public void save(studentDTO studentDTO) {
        student student = convertToModel(studentDTO);
        studentRepository.save(student);
    }

    public studentDTO convertToDTO(student student) {
        return new studentDTO(
                0,
                student.getFirstName(),
                student.getLastName(),
                student.getBirthDate(),
                student.getEmail(),
                student.getPhone(),
                student.getAddress(), 0
        );
    }

    public student convertToModel(studentDTO studentDTO) {
        student student = new student();
        student.setFirstName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setBirthDate(studentDTO.getBirthDate());
        student.setEmail(studentDTO.getEmail());
        student.setPhone(studentDTO.getPhone());
        student.setAddress(studentDTO.getAddress());
        // Aquí se debe manejar la asignación del Degree desde el ID
        return student;
    }
}

