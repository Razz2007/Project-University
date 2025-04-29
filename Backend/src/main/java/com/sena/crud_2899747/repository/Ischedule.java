package com.sena.crud_2899747.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.sena.crud_2899747.model.schedule;

public interface Ischedule extends JpaRepository<schedule, Integer> {

    // Si la entidad schedule tiene un campo 'status', se podría utilizar la siguiente consulta:
    // @Query("SELECT s FROM schedule s WHERE s.status != false")
    // List<schedule> getListScheduleActive();

    // Ejemplo de método derivado para buscar schedules por el día de la semana,
    // ignorando mayúsculas y minúsculas.
    List<schedule> findByDayOfWeekContainingIgnoreCase(String dayOfWeek);

    /*
     * C
     * R
     * U
     * D
     */
}
