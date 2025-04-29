package com.sena.crud_2899747.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.sena.crud_2899747.DTO.responseDTO;
import com.sena.crud_2899747.DTO.scheduleDTO;
import com.sena.crud_2899747.model.schedule;
import com.sena.crud_2899747.repository.Ischedule;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class scheduleService {

    @Autowired
    private Ischedule scheduleRepository;

    // Obtiene la lista de todos los schedules y los convierte a DTOs
    public List<scheduleDTO> findAll() {
        List<schedule> scheduleList = scheduleRepository.findAll();
        return scheduleList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Busca un schedule por su ID y lo convierte a DTO
    public Optional<scheduleDTO> findById(int id) {
        Optional<schedule> scheduleOpt = scheduleRepository.findById(id);
        if (scheduleOpt.isPresent()) {
            return Optional.of(convertToDTO(scheduleOpt.get()));
        }
        return Optional.empty();
    }

    // Elimina un schedule por su ID
    public responseDTO deleteSchedule(int id) {
        Optional<schedule> scheduleOpt = scheduleRepository.findById(id);
        if (!scheduleOpt.isPresent()) {
            return new responseDTO(
                    HttpStatus.NOT_FOUND.toString(),
                    "Error: El schedule no existe");
        }
        scheduleRepository.deleteById(id);
        return new responseDTO(
                HttpStatus.OK.toString(),
                "Schedule eliminado correctamente");
    }

    // Registra un nuevo schedule
    public responseDTO save(scheduleDTO scheduleDTO) {
        // Validar que el día de la semana no esté vacío
        if (scheduleDTO.getDayOfWeek() == null || scheduleDTO.getDayOfWeek().trim().isEmpty()) {
            return new responseDTO(
                    HttpStatus.BAD_REQUEST.toString(),
                    "El día de la semana es obligatorio");
        }
        schedule scheduleRegister = convertToModel(scheduleDTO);
        scheduleRepository.save(scheduleRegister);
        return new responseDTO(
                HttpStatus.OK.toString(),
                "Schedule guardado correctamente");
    }

    // Actualiza un schedule existente
    public responseDTO updateSchedule(int id, scheduleDTO dto) {
        Optional<schedule> scheduleOpt = scheduleRepository.findById(id);
        if (!scheduleOpt.isPresent()) {
            return new responseDTO(
                    HttpStatus.NOT_FOUND.toString(),
                    "Error: El schedule con ID " + id + " no existe");
        }
        // Validar nuevamente que el día de la semana no sea nulo o vacío
        if (dto.getDayOfWeek() == null || dto.getDayOfWeek().trim().isEmpty()) {
            return new responseDTO(
                    HttpStatus.BAD_REQUEST.toString(),
                    "El día de la semana es obligatorio");
        }
        schedule existingSchedule = scheduleOpt.get();
        existingSchedule.setDayOfWeek(dto.getDayOfWeek());
        existingSchedule.setStartTime(dto.getStartTime());
        existingSchedule.setEndTime(dto.getEndTime());
        scheduleRepository.save(existingSchedule);
        return new responseDTO(
                HttpStatus.OK.toString(),
                "Schedule actualizado correctamente");
    }

    // Convierte una entidad schedule a su correspondiente DTO
    public scheduleDTO convertToDTO(schedule schedule) {
        return new scheduleDTO(
                0,             // Se asume que la entidad schedule tiene un campo 'id'
                schedule.getDayOfWeek(),
                schedule.getStartTime(),
                schedule.getEndTime(),
                0, // Estos campos adicionales se mantienen en 0 o se pueden ajustar según el DTO
                0
        );
    }

    // Convierte un DTO scheduleDTO a una entidad schedule
    public schedule convertToModel(scheduleDTO scheduleDTO) {
        schedule schedule = new schedule();
        schedule.setDayOfWeek(scheduleDTO.getDayOfWeek());
        schedule.setStartTime(scheduleDTO.getStartTime());
        schedule.setEndTime(scheduleDTO.getEndTime());
        return schedule;
    }
}
