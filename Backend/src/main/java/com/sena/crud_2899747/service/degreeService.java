package com.sena.crud_2899747.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sena.crud_2899747.DTO.degreeDTO;
import com.sena.crud_2899747.DTO.responseDTO;
import com.sena.crud_2899747.model.degree;
import com.sena.crud_2899747.repository.Idegree;
import java.util.List;
import java.util.Optional;

@Service
public class degreeService {

    /*
     * save
     * findAll
     * findById
     * Delete
     */
    /* establish connection with the interface */
    @Autowired
    private Idegree data;

    public List<degree> findAll() {
        return data.findAll();
    }

    public Optional<degree> findById(int id) {
        return data.findById(id);
    }

    public responseDTO deleteDegree(int id) {
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
    public responseDTO save(degreeDTO degreeDTO) {
        // validación longitud del nombre
        if (degreeDTO.getName().length() < 1 ||
                degreeDTO.getName().length() > 50) {
            responseDTO respuesta = new responseDTO(
                    HttpStatus.BAD_REQUEST.toString(),
                    "El nombre debe estar entre 1 y 50 caracteres");
            return respuesta;
        }
        // otras condiciones que podrías agregar
        degree degreeRegister = converToModel(degreeDTO);
        data.save(degreeRegister);
        responseDTO respuesta = new responseDTO(
                HttpStatus.OK.toString(),
                "Se guardó correctamente");
        return respuesta;
    }

    public degreeDTO convertToDTO(degree degree) {
        degreeDTO degreedto = new degreeDTO(
            degree.getName(),
            degree.getDurationYears(),
            degree.getCoordinator(),
            degree.getFaculty()
        );
        return degreedto;
    }

    public degree converToModel(degreeDTO degreeDTO) {
        degree degree = new degree(
                0,
                degreeDTO.getName(),
                degreeDTO.getDurationYears(),
                degreeDTO.getCoordinator(),
                degreeDTO.getFaculty());
        return degree;
    }
}