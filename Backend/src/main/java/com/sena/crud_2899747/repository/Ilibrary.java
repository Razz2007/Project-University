package com.sena.crud_2899747.repository;

import com.sena.crud_2899747.model.library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface Ilibrary extends JpaRepository<library, Integer> {
    
    // Método para obtener las bibliotecas activas (status == true)
    List<library> findByStatusTrue();
    
    // Método para buscar bibliotecas cuyo nombre contenga el valor dado (ignorando mayúsculas y minúsculas)
    List<library> findByNameLibraryContainingIgnoreCase(String search);

}

