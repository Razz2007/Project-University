package com.sena.crud_2899747.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sena.crud_2899747.DTO.scheduleDTO;
import com.sena.crud_2899747.model.schedule;
import com.sena.crud_2899747.repository.Ischedule;

@Service
public class scheduleService {

    @Autowired
    private Ischedule scheduleRepository;

    // Registrar o actualizar un horario
    public void save(scheduleDTO scheduleDTO) {
        schedule schedule = convertToModel(scheduleDTO);
        scheduleRepository.save(schedule);
    }

    // Convertir `schedule` a `scheduleDTO`
    public scheduleDTO convertToDTO(schedule schedule) {
        return new scheduleDTO(
                0,
                schedule.getDayOfWeek(),
                schedule.getStartTime(),
                schedule.getEndTime(), 0, 0
        );
    }

    // Convertir `scheduleDTO` a `schedule`
    public schedule convertToModel(scheduleDTO scheduleDTO) {
        schedule schedule = new schedule();
        schedule.setDayOfWeek(scheduleDTO.getDayOfWeek());
        schedule.setStartTime(scheduleDTO.getStartTime());
        schedule.setEndTime(scheduleDTO.getEndTime());
        return schedule;
    }
}
