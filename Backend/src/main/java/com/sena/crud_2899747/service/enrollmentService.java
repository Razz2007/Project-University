package com.sena.crud_2899747.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.sena.crud_2899747.DTO.enrollmentDTO;
import com.sena.crud_2899747.DTO.responseDTO;
import com.sena.crud_2899747.model.enrollment;
import com.sena.crud_2899747.repository.Ienrollment;
import java.util.List;
import java.util.Optional;

@Service
public class enrollmentService {

    @Autowired
    private Ienrollment data;

    /**
     * Obtiene la lista de inscripciones activas.
     * Se utiliza el método del repositorio que retorna únicamente aquellas
     * inscripciones cuyo status es distinto de 'CANCELED'.
     *
     * @return Lista de enrollment (entidades activas)
     */
    public List<enrollment> findAll() {
        return data.getListEnrollmentActive();
    }

    /**
     * Busca una inscripción por su ID.
     *
     * @param id Identificador de la inscripción
     * @return Optional con la inscripción
     */
    public Optional<enrollment> findById(int id) {
        return data.findById(id);
    }

    /**
     * Elimina una inscripción según su ID.
     *
     * @param id Identificador de la inscripción a eliminar
     * @return responseDTO con el resultado de la operación
     */
    public responseDTO deleteEnrollment(int id) {
        Optional<enrollment> enrollmentOpt = findById(id);
        if (!enrollmentOpt.isPresent()) {
            return new responseDTO(
                    HttpStatus.NOT_FOUND.toString(),
                    "Error: La inscripción no existe");
        }
        data.deleteById(id);
        return new responseDTO(
                HttpStatus.OK.toString(),
                "Inscripción eliminada correctamente");
    }

    /**
     * Registra una nueva inscripción.
     * Se valida que el campo semestre no sea nulo ni vacío.
     *
     * @param dto DTO con los datos de la inscripción
     * @return responseDTO con el resultado de la operación
     */
    public responseDTO save(enrollmentDTO dto) {
        if (dto.getSemester() == null || dto.getSemester().trim().isEmpty()) {
            return new responseDTO(
                    HttpStatus.BAD_REQUEST.toString(),
                    "El semestre es obligatorio");
        }

        enrollment enrollmentRegister = convertToModel(dto);
        data.save(enrollmentRegister);
        return new responseDTO(
                HttpStatus.OK.toString(),
                "Inscripción guardada correctamente");
    }

    /**
     * Actualiza la información de una inscripción existente.
     *
     * @param id  ID de la inscripción a actualizar
     * @param dto DTO con los nuevos datos para la inscripción
     * @return responseDTO con el resultado de la operación
     */
    public responseDTO updateEnrollment(int id, enrollmentDTO dto) {
        Optional<enrollment> enrollmentOpt = data.findById(id);
        if (!enrollmentOpt.isPresent()) {
            return new responseDTO(
                    HttpStatus.NOT_FOUND.toString(),
                    "Error: La inscripción con ID " + id + " no existe");
        }
        if (dto.getSemester() == null || dto.getSemester().trim().isEmpty()) {
            return new responseDTO(
                    HttpStatus.BAD_REQUEST.toString(),
                    "El semestre es obligatorio");
        }
        enrollment existingEnrollment = enrollmentOpt.get();
        existingEnrollment.setSemester(dto.getSemester());
        existingEnrollment.setStatusEnrollment(dto.getStatusEnrollment());
        existingEnrollment.setGrade(dto.getGrade());
        existingEnrollment.setEnrollmentDate(dto.getEnrollmentDate());

        data.save(existingEnrollment);
        return new responseDTO(
                HttpStatus.OK.toString(),
                "Inscripción actualizada correctamente");
    }

    /**
     * Convierte una entidad enrollment en un DTO.
     *
     * @param enrollmentEntity Entidad enrollment
     * @return DTO de enrollment
     */
    public enrollmentDTO convertToDTO(enrollment enrollmentEntity) {
        return new enrollmentDTO(
                enrollmentEntity.getEnrollmentId(),
                enrollmentEntity.getEnrollmentDate(),
                enrollmentEntity.getSemester(),
                enrollmentEntity.getGrade(),
                enrollmentEntity.getStatusEnrollment(),
                enrollmentEntity.getStatus()
        );
    }
    
    /**
     * Convierte un DTO en una entidad enrollment.
     *
     * @param enrollmentDTO DTO de enrollment
     * @return Entidad enrollment
     */
    public enrollment convertToModel(enrollmentDTO enrollmentDTO) {
        return new enrollment(
                0,
                enrollmentDTO.getEnrollmentDate(),
                enrollmentDTO.getSemester(),
                enrollmentDTO.getGrade(),
                enrollmentDTO.getStatusEnrollment(),
                enrollmentDTO.isStatus()
        );
    }   
}
