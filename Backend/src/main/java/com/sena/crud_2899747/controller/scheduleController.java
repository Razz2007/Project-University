package com.sena.crud_2899747.controller;

import com.sena.crud_2899747.DTO.responseDTO;
import com.sena.crud_2899747.DTO.scheduleDTO;
import com.sena.crud_2899747.service.scheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/schedule")
public class scheduleController {

    @Autowired
    private scheduleService scheduleService;

    // POST: Registrar un nuevo schedule
    @PostMapping("/")
    public ResponseEntity<Object> registerSchedule(@RequestBody scheduleDTO schedule) {
        responseDTO response = scheduleService.save(schedule);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // GET: Obtener la lista de todos los schedules
    @GetMapping("/")
    public ResponseEntity<Object> getAllSchedules() {
        List<scheduleDTO> scheduleList = scheduleService.findAll();
        return new ResponseEntity<>(scheduleList, HttpStatus.OK);
    }

    // GET: Obtener un schedule por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getScheduleById(@PathVariable int id) {
        Optional<scheduleDTO> scheduleOpt = scheduleService.findById(id);
        if (!scheduleOpt.isPresent()) {
            return new ResponseEntity<>(new responseDTO(HttpStatus.NOT_FOUND.toString(), "Schedule no encontrado"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(scheduleOpt.get(), HttpStatus.OK);
    }

    // PUT: Actualizar el schedule existente
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateSchedule(@PathVariable int id, @RequestBody scheduleDTO schedule) {
        responseDTO response = scheduleService.updateSchedule(id, schedule);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // DELETE: Eliminar un schedule por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSchedule(@PathVariable int id) {
        responseDTO response = scheduleService.deleteSchedule(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
