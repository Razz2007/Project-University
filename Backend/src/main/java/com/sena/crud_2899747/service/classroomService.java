package com.sena.crud_2899747.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sena.crud_2899747.DTO.classroomDTO;
import com.sena.crud_2899747.DTO.responseDTO;
import com.sena.crud_2899747.model.classroom;
import com.sena.crud_2899747.repository.Iclassroom;

import java.util.List;
import java.util.Optional;

@Service
public class classroomService {

    @Autowired
    private Iclassroom data;

    // Obtener todas las aulas, en este caso se asume que se obtienen todas las registradas.
    public List<classroom> findAll() {
        return data.findAll();
    }

    // Obtener la lista de aulas filtrando por nombre (se asume que en el repositorio existe este método)
    public List<classroom> getListClassroomForName(String filter) {
        return data.findByNameClassroomContainingIgnoreCase(filter);
    }

    // Obtener un aula por su ID
    public Optional<classroom> findById(int id) {
        return data.findById(id);
    }

    // Eliminar un aula
    public responseDTO deleteClassroom(int id) {
        Optional<classroom> classroomOptional = findById(id);
        if (!classroomOptional.isPresent()) {
            return new responseDTO(
                    HttpStatus.NOT_FOUND.toString(),
                    "Error: El aula no existe");
        }
        data.deleteById(id);
        return new responseDTO(
                HttpStatus.OK.toString(),
                "Aula eliminada correctamente");
    }

    // Registrar un aula con validaciones
    public responseDTO save(classroomDTO classroomDTO) {
        // Validación de la longitud del nombre del aula
        if (classroomDTO.getName_classroom() == null || classroomDTO.getName_classroom().length() < 1 ||
                classroomDTO.getName_classroom().length() > 50) {
            return new responseDTO(
                    HttpStatus.BAD_REQUEST.toString(),
                    "El nombre del aula debe estar entre 1 y 50 caracteres");
        }
        // Validación de la capacidad
        if (classroomDTO.getCapacity() <= 0) {
            return new responseDTO(
                    HttpStatus.BAD_REQUEST.toString(),
                    "La capacidad debe ser mayor que cero");
        }
        // Validación del número de edificio
        if (classroomDTO.getBuilding() <= 0) {
            return new responseDTO(
                    HttpStatus.BAD_REQUEST.toString(),
                    "El número de edificio debe ser mayor que cero");
        }
        // Validación del tipo de aula
        if (classroomDTO.getType() == null || classroomDTO.getType().isEmpty()) {
            return new responseDTO(
                    HttpStatus.BAD_REQUEST.toString(),
                    "El tipo de aula no puede estar vacío");
        }

        classroom classroomRegister = converToModel(classroomDTO);
        data.save(classroomRegister);
        return new responseDTO(
                HttpStatus.OK.toString(),
                "Aula guardada correctamente");
    }

    // Actualizar un aula existente
    public responseDTO updateClassroom(int id, classroomDTO dto) {
        Optional<classroom> classroomOptional = data.findById(id);
        if (!classroomOptional.isPresent()) {
            return new responseDTO(
                    HttpStatus.NOT_FOUND.toString(),
                    "Error: El aula con ID " + id + " no existe");
        }
        // Validaciones similares a la creación
        if (dto.getName_classroom() == null || dto.getName_classroom().length() < 1 ||
                dto.getName_classroom().length() > 50) {
            return new responseDTO(
                    HttpStatus.BAD_REQUEST.toString(),
                    "El nombre del aula debe estar entre 1 y 50 caracteres");
        }
        if (dto.getCapacity() <= 0) {
            return new responseDTO(
                    HttpStatus.BAD_REQUEST.toString(),
                    "La capacidad debe ser mayor que cero");
        }
        if (dto.getBuilding() <= 0) {
            return new responseDTO(
                    HttpStatus.BAD_REQUEST.toString(),
                    "El número de edificio debe ser mayor que cero");
        }
        if (dto.getType() == null || dto.getType().isEmpty()) {
            return new responseDTO(
                    HttpStatus.BAD_REQUEST.toString(),
                    "El tipo de aula no puede estar vacío");
        }

        classroom existingClassroom = classroomOptional.get();
        existingClassroom.setName_classroom(dto.getName_classroom());
        existingClassroom.setBuilding(dto.getBuilding());
        existingClassroom.setCapacity(dto.getCapacity());
        existingClassroom.setType(dto.getType());
        existingClassroom.setHas_projector(dto.getHas_projector());

        data.save(existingClassroom);
        return new responseDTO(
                HttpStatus.OK.toString(),
                "Aula actualizada correctamente");
    }

    // Convertir entidad classroom a classroomDTO
    public classroomDTO convertToDTO(classroom classroom) {
        return new classroomDTO(
                classroom.getName_classroom(),
                classroom.getBuilding(),
                classroom.getCapacity(),
                classroom.getType(),
                classroom.getHas_projector());
    }

    // Convertir classroomDTO a entidad classroom
    public classroom converToModel(classroomDTO classroomDTO) {
        return new classroom(
                0,
                classroomDTO.getName_classroom(),
                classroomDTO.getBuilding(),
                classroomDTO.getCapacity(),
                classroomDTO.getType(),
                classroomDTO.getHas_projector(),
                true // Asumiendo que el estado por defecto es activo (true)
                );
    }
}
