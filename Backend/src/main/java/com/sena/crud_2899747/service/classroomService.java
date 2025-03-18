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

    /*
     * save
     * findAll
     * findById
     * Delete
     */
    /* establish connection with the interface */
    @Autowired
    private Iclassroom data;

    public List<classroom> findAll() {
        return data.findAll();
    }

    public Optional<classroom> findById(int id) {
        return data.findById(id);
    }

    public responseDTO deleteClassroom(int id) {
        if (!findById(id).isPresent()) {
            responseDTO respuesta = new responseDTO(
                    HttpStatus.OK.toString(),
                    "El registro no existe");
            return respuesta;
        }
        data.deleteById(id);
        responseDTO respuesta = new responseDTO(
                HttpStatus.OK.toString(),
                "Se eliminó correctamente");
        return respuesta;
    }

    // register and update
    public responseDTO save(classroomDTO classroomDTO) {
        // validación longitud del nombre
        if (classroomDTO.getName_classroom().length() < 1 ||
                classroomDTO.getName_classroom().length() > 50) {
            responseDTO respuesta = new responseDTO(
                    HttpStatus.BAD_REQUEST.toString(),
                    "El nombre del aula debe estar entre 1 y 50 caracteres");
            return respuesta;
        }
        
        // validación capacidad
        if (classroomDTO.getCapacity() <= 0) {
            responseDTO respuesta = new responseDTO(
                    HttpStatus.BAD_REQUEST.toString(),
                    "La capacidad debe ser mayor que cero");
            return respuesta;
        }
        
        // validación edificio
        if (classroomDTO.getBuilding() <= 0) {
            responseDTO respuesta = new responseDTO(
                    HttpStatus.BAD_REQUEST.toString(),
                    "El número de edificio debe ser mayor que cero");
            return respuesta;
        }
        
        // validación tipo
        if (classroomDTO.getType() == null || classroomDTO.getType().isEmpty()) {
            responseDTO respuesta = new responseDTO(
                    HttpStatus.BAD_REQUEST.toString(),
                    "El tipo de aula no puede estar vacío");
            return respuesta;
        }

        classroom classroomRegister = converToModel(classroomDTO);
        data.save(classroomRegister);
        responseDTO respuesta = new responseDTO(
                HttpStatus.OK.toString(),
                "Se guardó correctamente");
        return respuesta;
    }

    public classroomDTO convertToDTO(classroom classroom) {
        classroomDTO classroomdto = new classroomDTO(
            classroom.getName_classroom(),
            classroom.getBuilding(),
            classroom.getCapacity(),
            classroom.getType(),
            classroom.getHas_projector()
        );
        return classroomdto;
    }

    public classroom converToModel(classroomDTO classroomDTO) {
        classroom classroom = new classroom(
                0,
                classroomDTO.getName_classroom(),
                classroomDTO.getBuilding(),
                classroomDTO.getCapacity(),
                classroomDTO.getType(),
                classroomDTO.getHas_projector());
        return classroom;
    }
}