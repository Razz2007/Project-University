package com.sena.crud_2899747.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sena.crud_2899747.DTO.degreeDTO;
import com.sena.crud_2899747.model.degree;
import com.sena.crud_2899747.repository.Idegree;

@Service
public class degreeService {

    @Autowired
    private Idegree degreeRepository;

    // Registrar un nuevo grado
    public void save(degreeDTO degreeDTO) {
        degree degree = convertToModel(degreeDTO);
        degreeRepository.save(degree);
    }

    // Convertir de `degreeDTO` a `degree`
    private degree convertToModel(degreeDTO degreeDTO) {
        return new degree(
                degreeDTO.getDegreeId(),
                degreeDTO.getName(),
                degreeDTO.getDurationYears(),
                degreeDTO.getCoordinator(),
                degreeDTO.getFaculty()
        );
    }
}
