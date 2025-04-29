package com.sena.crud_2899747.service;

import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sena.crud_2899747.DTO.responseDTO;
import com.sena.crud_2899747.DTO.professorDTO;
import com.sena.crud_2899747.model.professor;
import com.sena.crud_2899747.repository.Iprofessor;

@Service
public class professorService {

    @Autowired
    private Iprofessor data;

    // Obtiene la lista de todos los profesores
    public List<professor> findAll() {
        return data.findAll();
    }

    // Busca un profesor por su ID
    public Optional<professor> findById(int id) {
        return data.findById(id);
    }

    // Elimina un profesor según su ID
    public responseDTO deleteProfessor(int id) {
        Optional<professor> profOpt = findById(id);
        if (profOpt.isEmpty()) {
            return new responseDTO(HttpStatus.NOT_FOUND.toString(), "Error: El profesor no existe.");
        }
        data.deleteById(id);
        return new responseDTO(HttpStatus.OK.toString(), "Profesor eliminado correctamente.");
    }

    // Guarda un nuevo profesor con validaciones
    public responseDTO save(professorDTO professorDTO) {
        responseDTO validationResponse = validateProfessor(professorDTO);
        if (validationResponse != null) return validationResponse;

        professor professorRegister = convertToModel(professorDTO);
        data.save(professorRegister);
        return new responseDTO(HttpStatus.OK.toString(), "Profesor guardado correctamente.");
    }

    // Actualiza la información de un profesor existente
    public responseDTO updateProfessor(int id, professorDTO dto) {
        Optional<professor> profOpt = data.findById(id);
        if (profOpt.isEmpty()) {
            return new responseDTO(HttpStatus.NOT_FOUND.toString(), "Error: El profesor con ID " + id + " no existe.");
        }

        responseDTO validationResponse = validateProfessor(dto);
        if (validationResponse != null) return validationResponse;

        professor existingProfessor = profOpt.get();
        existingProfessor.setFirstName(dto.getFirstName());
        existingProfessor.setLastName(dto.getLastName());
        existingProfessor.setSpecialty(dto.getSpecialty());
        existingProfessor.setHireDate(dto.getHireDate());
        existingProfessor.setEmail(dto.getEmail());
        existingProfessor.setPhone(dto.getPhone());
        existingProfessor.setSalary(dto.getSalary());
        existingProfessor.setDepartmentId(dto.getDepartmentId());

        data.save(existingProfessor);
        return new responseDTO(HttpStatus.OK.toString(), "Profesor actualizado correctamente.");
    }

    // Validaciones comunes para evitar código repetitivo
    private responseDTO validateProfessor(professorDTO dto) {
        if (dto.getFirstName() == null || dto.getFirstName().isEmpty() || dto.getFirstName().length() > 50) {
            return new responseDTO(HttpStatus.BAD_REQUEST.toString(), "El nombre debe tener entre 1 y 50 caracteres.");
        }
        if (dto.getLastName() == null || dto.getLastName().isEmpty() || dto.getLastName().length() > 50) {
            return new responseDTO(HttpStatus.BAD_REQUEST.toString(), "El apellido debe tener entre 1 y 50 caracteres.");
        }
        if (dto.getSpecialty() == null || dto.getSpecialty().isEmpty()) {
            return new responseDTO(HttpStatus.BAD_REQUEST.toString(), "La especialidad es obligatoria.");
        }
        if (dto.getHireDate() == null) {
            return new responseDTO(HttpStatus.BAD_REQUEST.toString(), "La fecha de contratación es obligatoria.");
        }
        if (dto.getSalary() == null || dto.getSalary().compareTo(BigDecimal.ZERO) <= 0) {
            return new responseDTO(HttpStatus.BAD_REQUEST.toString(), "El salario debe ser mayor que 0.");
        }
        return null; // Sin errores
    }

    // Conversión de `professor` a `professorDTO`
    public professorDTO convertToDTO(professor professor) {
        return new professorDTO(
                professor.getProfessorId(),
                professor.getFirstName(),
                professor.getLastName(),
                professor.getSpecialty(),
                professor.getHireDate(),
                professor.getEmail(),
                professor.getPhone(),
                professor.getSalary(),
                professor.getDepartmentId()
        );
    }

    // Conversión de `professorDTO` a `professor`
    public professor convertToModel(professorDTO professorDTO) {
        return new professor(
                professorDTO.getProfessorId(),
                professorDTO.getFirstName(),
                professorDTO.getLastName(),
                professorDTO.getSpecialty(),
                professorDTO.getHireDate(),
                professorDTO.getEmail(),
                professorDTO.getPhone(),
                professorDTO.getSalary(),
                professorDTO.getDepartmentId()
        );
    }
}
