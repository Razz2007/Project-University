package com.sena.crud_2899747.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.sena.crud_2899747.model.department;

public interface Idepartment extends JpaRepository<department, Integer> {

    // Consulta para obtener únicamente los departamentos activos
    @Query("SELECT d FROM department d WHERE d.status != false")
    List<department> getListDepartmentActive();

    // Método derivado para filtrar departamentos por nombre (ignorando mayúsculas y minúsculas)
    List<department> findByDepartmentNameContainingIgnoreCase(String name);

    /*
     * C
     * R
     * U
     * D
     */
}
