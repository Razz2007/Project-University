package com.sena.crud_2899747.controller;

import org.springframework.web.bind.annotation.RestController;

import com.sena.crud_2899747.DTO.responseDTO;
import com.sena.crud_2899747.model.enrollment;
import com.sena.crud_2899747.DTO.enrollmentDTO;
import com.sena.crud_2899747.service.enrollmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/enrollment")
public class enrollmentController {

    /*
     * GET
     * POST(REGISTER)
     * PUT
     * DELETE
     */
    @Autowired
    private enrollmentService enrollmentService;

    @PostMapping("/")   
    public ResponseEntity<Object> registerEnrollment(@RequestBody enrollmentDTO enrollment) {
        responseDTO respuesta = enrollmentService.save(enrollment);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<Object> getAllEnrollments() {
        var listaEnrollments = enrollmentService.findAll();
        return new ResponseEntity<>(listaEnrollments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneEnrollment(@PathVariable int id) {
        Optional<enrollment> enrollmentOpt = enrollmentService.findById(id);
        if (!enrollmentOpt.isPresent())
            return new ResponseEntity<>(
                    new responseDTO(HttpStatus.NOT_FOUND.toString(), "Inscripción no encontrada"),
                    HttpStatus.NOT_FOUND
            );
        return new ResponseEntity<>(enrollmentOpt.get(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEnrollment(@PathVariable int id) {
        responseDTO respuesta = enrollmentService.deleteEnrollment(id);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEnrollment(@PathVariable int id, @RequestBody enrollmentDTO dto) {
        responseDTO respuesta = enrollmentService.updateEnrollment(id, dto);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
