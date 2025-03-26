package com.sena.crud_2899747.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sena.crud_2899747.DTO.responseDTO;
import com.sena.crud_2899747.DTO.degreeDTO;
import com.sena.crud_2899747.model.degree;
import com.sena.crud_2899747.repository.Idegree;

import java.time.LocalDateTime;
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
        return data.getListDegreeActive();
    }

    public List<degree> getListDegreeForName(String filter) {
        return data.getListDegreeForName(filter);
    }

    public Optional<degree> findById(int id) {
        return data.findById(id);
    }

    public responseDTO deleteDegree(int id) {
        Optional<degree> degree = findById(id);
        if (!degree.isPresent()) {
            responseDTO respuesta = new responseDTO(
                    HttpStatus.OK.toString(),
                    "The register does not exist");
            return respuesta;
        }
        degree.get().setStatus(false);
        data.save(degree.get());
        
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
                degree.getFaculty());
        return degreedto;
    }

    public degree converToModel(degreeDTO degreeDTO) {
        degree degree = new degree(
                0,
                degreeDTO.getName(),
                degreeDTO.getDurationYears(),
                degreeDTO.getCoordinator(),
                degreeDTO.getFaculty(),
                true);
        return degree;
    }
}