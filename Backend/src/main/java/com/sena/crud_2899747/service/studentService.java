package com.sena.crud_2899747.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sena.crud_2899747.DTO.responseDTO;
import com.sena.crud_2899747.DTO.studentDTO;
import com.sena.crud_2899747.model.student;
import com.sena.crud_2899747.model.degree;
import com.sena.crud_2899747.repository.Istudent;
import com.sena.crud_2899747.repository.Idegree;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class studentService {

    @Autowired
    private Istudent data;
    
    // Inyección del repositorio de degree para obtener el degree persistido
    @Autowired
    private Idegree degreeRepository;

    // Obtiene la lista de todos los estudiantes y los convierte a DTOs
    public List<studentDTO> findAll() {
        List<student> studentList = data.findAll();
        return studentList.stream()
                          .map(this::convertToDTO)
                          .collect(Collectors.toList());
    }

    // Método nuevo para filtrar estudiantes por primer nombre (sin distinguir mayúsculas/minúsculas)
    public List<studentDTO> getListStudentForFirstName(String filter) {
        List<student> studentList = data.findByFirstNameContainingIgnoreCase(filter);
        return studentList.stream()
                          .map(this::convertToDTO)
                          .collect(Collectors.toList());
    }

    // Busca un estudiante por su ID y lo retorna como DTO
    public Optional<studentDTO> findById(int id) {
        Optional<student> studentOpt = data.findById(id);
        if (studentOpt.isPresent()) {
            return Optional.of(convertToDTO(studentOpt.get()));
        }
        return Optional.empty();
    }

    // Elimina un estudiante según su ID
    public responseDTO deleteStudent(int id) {
        Optional<student> studentOpt = data.findById(id);
        if (!studentOpt.isPresent()) {
            return new responseDTO(
                    HttpStatus.NOT_FOUND.toString(),
                    "Error: El estudiante no existe"
            );
        }
        data.deleteById(id);
        return new responseDTO(
                    HttpStatus.OK.toString(),
                    "Estudiante eliminado correctamente"
        );
    }

    // Registra un nuevo estudiante con validación básica del primer nombre
    public responseDTO save(studentDTO studentDTO) {
        if (studentDTO.getFirstName() == null ||
            studentDTO.getFirstName().trim().length() < 1 ||
            studentDTO.getFirstName().trim().length() > 50) {
            return new responseDTO(
                    HttpStatus.BAD_REQUEST.toString(),
                    "El nombre debe estar entre 1 y 50 caracteres"
            );
        }
        student studentRegister = convertToModel(studentDTO);
        data.save(studentRegister);
        return new responseDTO(
                    HttpStatus.OK.toString(),
                    "Estudiante guardado correctamente"
        );
    }

    // Actualiza la información de un estudiante existente
    public responseDTO updateStudent(int id, studentDTO dto) {
        Optional<student> studentOpt = data.findById(id);
        if (!studentOpt.isPresent()) {
            return new responseDTO(
                    HttpStatus.NOT_FOUND.toString(),
                    "Error: El estudiante con ID " + id + " no existe"
            );
        }
        if (dto.getFirstName() == null ||
            dto.getFirstName().trim().length() < 1 ||
            dto.getFirstName().trim().length() > 50) {
            return new responseDTO(
                    HttpStatus.BAD_REQUEST.toString(),
                    "El nombre debe estar entre 1 y 50 caracteres"
            );
        }
        student existingStudent = studentOpt.get();
        existingStudent.setFirstName(dto.getFirstName());
        existingStudent.setLastName(dto.getLastName());
        existingStudent.setBirthDate(dto.getBirthDate());
        existingStudent.setEmail(dto.getEmail());
        existingStudent.setPhone(dto.getPhone());
        existingStudent.setAddress(dto.getAddress());
        
        // Aquí obtenemos el degree persistido para evitar el error de instancia transitoria
        int degreeIdValue = dto.getDegreeId().getId();
        degree persistentDegree = degreeRepository.findById(degreeIdValue)
            .orElseThrow(() -> new RuntimeException("El grado con ID " + degreeIdValue + " no existe"));
        existingStudent.setDegreeId(persistentDegree);
        
        data.save(existingStudent);
        return new responseDTO(
                    HttpStatus.OK.toString(),
                    "Estudiante actualizado correctamente"
        );
    }

    // Convierte la entidad student a su correspondiente DTO
    public studentDTO convertToDTO(student studentEntity) {
        return new studentDTO(    
                studentEntity.getStudentId(),
                studentEntity.getFirstName(),
                studentEntity.getLastName(),
                studentEntity.getBirthDate(),
                studentEntity.getEmail(),
                studentEntity.getPhone(),
                studentEntity.getAddress(),
                studentEntity.getDegreeId() // Se asume un mapeo correspondiente en el DTO
        );
    }

    // Convierte un DTO studentDTO a la entidad student
    public student convertToModel(studentDTO studentDTO) {
        // Se asume que studentDTO.getDegreeId() devuelve un objeto (por ejemplo, DegreeDTO) que contiene el id del grado.
        int degreeIdValue = studentDTO.getDegreeId().getId();
        degree persistentDegree = degreeRepository.findById(degreeIdValue)
            .orElseThrow(() -> new RuntimeException("El grado con ID " + degreeIdValue + " no existe"));
        return new student(
                0,  // Se asume que el ID se asigna automáticamente
                studentDTO.getFirstName(),
                studentDTO.getLastName(),
                studentDTO.getBirthDate(),
                studentDTO.getEmail(),
                studentDTO.getPhone(),
                studentDTO.getAddress(),
                persistentDegree,
                true
        );
    }
}
