package com.sena.crud_2899747.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sena.crud_2899747.model.professor;

public interface Iprofessor extends JpaRepository<professor, Integer> {

    // Si la entidad 'professor' tuviera un campo que indique si está activo,
    // podrías incluir una consulta personalizada similar a la siguiente:
    // @Query("SELECT p FROM professor p WHERE p.status = true")
    // List<professor> getActiveProfessors();

    // Método derivado para buscar profesores por su primer nombre, ignorando mayúsculas y minúsculas.
    List<professor> findByFirstNameContainingIgnoreCase(String firstName);

    /*
     * C (Create), R (Read), U (Update), D (Delete)
     */
}
