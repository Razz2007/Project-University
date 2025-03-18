package com.sena.crud_2899747.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sena.crud_2899747.model.schedule;

public interface Ischedule extends JpaRepository<schedule, Integer> {
    // Aquí puedes agregar consultas personalizadas si las necesitas en el futuro
}
