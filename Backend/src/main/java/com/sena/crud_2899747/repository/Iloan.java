package com.sena.crud_2899747.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.sena.crud_2899747.model.loan;

public interface Iloan extends JpaRepository<loan, Integer> {

    // Obtener préstamos activos correctamente
    @Query("SELECT l FROM loan l WHERE l.status = true")
    List<loan> getActiveLoans();

    // Buscar préstamos por nombre de estudiante
    @Query("SELECT l FROM loan l WHERE LOWER(l.student.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(l.student.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<loan> findByStudentName(@Param("name") String name);
}
