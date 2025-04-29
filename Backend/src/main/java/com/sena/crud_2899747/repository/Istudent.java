package com.sena.crud_2899747.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.sena.crud_2899747.model.student;

public interface Istudent extends JpaRepository<student, Integer> {

    // Consulta para obtener únicamente los estudiantes activos.
    // Se asume que la entidad student tiene un campo booleano 'status'.
    @Query("SELECT s FROM student s WHERE s.status = true")
    List<student> getActiveStudents();

    // Método derivado para buscar estudiantes por su primer nombre (ignorando mayúsculas y minúsculas)
    List<student> findByFirstNameContainingIgnoreCase(String firstName);

    /*
     * C
     * R
     * U
     * D
     */
}
