package com.sena.crud_2899747.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sena.crud_2899747.model.classroom;

public interface Iclassroom extends JpaRepository<classroom, Integer> {

    // Si tu entidad 'classroom' maneja un campo 'status', podrías incluir esta consulta:
    @Query("SELECT c FROM classroom c WHERE c.status != false")
    List<classroom> getListClassroomActive();

    // Método para buscar aulas por nombre (ignora mayúsculas y minúsculas)
    List<classroom> findByNameClassroomContainingIgnoreCase(String name);

    /*
     * C
     * R
     * U
     * D
     */
}
