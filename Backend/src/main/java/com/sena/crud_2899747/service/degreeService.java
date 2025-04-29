package com.sena.crud_2899747.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.sena.crud_2899747.DTO.responseDTO;
import com.sena.crud_2899747.DTO.degreeDTO;
import com.sena.crud_2899747.model.degree;
import com.sena.crud_2899747.repository.Idegree;
import java.util.List;
import java.util.Optional;

@Service
public class degreeService {

    @Autowired
    private Idegree data;

    public List<degree> findAll() {
        return data.getListDegreeActive();
    }

    public List<degree> getListDegreeForName(String filter) {
        return data.findByNameContainingIgnoreCase(filter);
    }

    public Optional<degree> findById(int id) {
        return data.findById(id);
    }

    public responseDTO deleteDegree(int id) {
        Optional<degree> degree = findById(id);
        if (!degree.isPresent()) {
            return new responseDTO(
                    HttpStatus.NOT_FOUND.toString(),
                    "Error: El grado académico no existe");
        }
        
        data.deleteById(id); // Ahora realmente eliminamos el registro
        
        return new responseDTO(HttpStatus.OK.toString(),
                "Grado académico eliminado correctamente");
    }

    public responseDTO save(degreeDTO degreeDTO) {
        if (degreeDTO.getName().length() < 1 ||
                degreeDTO.getName().length() > 50) {
            return new responseDTO(
                    HttpStatus.BAD_REQUEST.toString(),
                    "El nombre debe estar entre 1 y 50 caracteres");
        }
        
        degree degreeRegister = converToModel(degreeDTO);
        data.save(degreeRegister);
        return new responseDTO(
                HttpStatus.OK.toString(),
                "Grado académico guardado correctamente");
    }

    public responseDTO updateDegree(int id, degreeDTO dto) {
        Optional<degree> degreeOpt = data.findById(id);
        if (!degreeOpt.isPresent()) {
            return new responseDTO(
                    HttpStatus.NOT_FOUND.toString(),
                    "Error: El grado académico con ID " + id + " no existe");
        }
    
        if (dto.getName().length() < 1 || dto.getName().length() > 50) {
            return new responseDTO(
                    HttpStatus.BAD_REQUEST.toString(),
                    "El nombre debe estar entre 1 y 50 caracteres");
        }
    
        degree existingDegree = degreeOpt.get();
        existingDegree.setName(dto.getName());
        existingDegree.setDuration_years(dto.getDuration_years());
        existingDegree.setCoordinator(dto.getCoordinator());
        existingDegree.setFaculty(dto.getFaculty());

        data.save(existingDegree);
    
        return new responseDTO(
                HttpStatus.OK.toString(),
                "Grado académico actualizado correctamente");
    }

    public degreeDTO convertToDTO(degree degree) {
        return new degreeDTO(
                degree.getName(),
                degree.getDuration_years(),
                degree.getCoordinator(),
                degree.getFaculty());
    }

    public degree converToModel(degreeDTO degreeDTO) {
        return new degree(
                0,
                degreeDTO.getName(),
                degreeDTO.getDuration_years(),
                degreeDTO.getCoordinator(),
                degreeDTO.getFaculty(),
                true);
    }
}
