package com.sena.crud_2899747.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sena.crud_2899747.DTO.departmentDTO;
import com.sena.crud_2899747.DTO.responseDTO;
import com.sena.crud_2899747.model.department;
import com.sena.crud_2899747.repository.Idepartment;

import java.util.List;
import java.util.Optional;

@Service
public class departmentService {

    /*
     * save
     * findAll
     * findById
     * Delete
     */
    /* establish connection with the interface */
    @Autowired
    private Idepartment data;

    public List<department> findAll() {
        return data.findAll();
    }

    public Optional<department> findById(int id) {
        return data.findById(id);
    }

    public responseDTO deleteDepartment(int id) {
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
    public responseDTO save(departmentDTO departmentDTO) {
        // validación longitud del nombre
        if (departmentDTO.getName_department().length() < 1 ||
                departmentDTO.getName_department().length() > 50) {
            responseDTO respuesta = new responseDTO(
                    HttpStatus.BAD_REQUEST.toString(),
                    "El nombre del departamento debe estar entre 1 y 50 caracteres");
            return respuesta;
        }
        // validación presupuesto
        if (departmentDTO.getBudget() <= 0) {
            responseDTO respuesta = new responseDTO(
                    HttpStatus.BAD_REQUEST.toString(),
                    "El presupuesto debe ser mayor que cero");
            return respuesta;
        }

        department departmentRegister = converToModel(departmentDTO);
        data.save(departmentRegister);
        responseDTO respuesta = new responseDTO(
                HttpStatus.OK.toString(),
                "Se guardó correctamente");
        return respuesta;
    }

    public departmentDTO convertToDTO(department department) {
        departmentDTO departmentdto = new departmentDTO(
            department.getName_department(),
            department.getLocation_department(),
            department.getDirector(),
            department.getBudget()
        );
        return departmentdto;
    }

    public department converToModel(departmentDTO departmentDTO) {
        department department = new department(
                0,
                departmentDTO.getName_department(),
                departmentDTO.getLocation_department(),
                departmentDTO.getDirector(),
                departmentDTO.getBudget());
        return department;
    }
}