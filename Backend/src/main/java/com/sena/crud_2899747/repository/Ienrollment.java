package com.sena.crud_2899747.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.sena.crud_2899747.model.enrollment;

@Repository
public interface Ienrollment extends JpaRepository<enrollment, Integer> {
    
    // Consulta para obtener inscripciones activas (asumiendo que puede haber un campo status)
    @Query("SELECT e FROM enrollment e WHERE e.status = true")
    List<enrollment> getListEnrollmentActive();
    
    // Buscar por estado ignorando mayúsculas/minúsculas
    List<enrollment> findByStatusEnrollmentIgnoreCase(String statusEnrollment);
    
    // Buscar por semestre ignorando mayúsculas/minúsculas
    List<enrollment> findBySemesterIgnoreCase(String semester);
}
