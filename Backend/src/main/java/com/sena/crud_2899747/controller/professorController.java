package com.sena.crud_2899747.controller;

import com.sena.crud_2899747.DTO.professorDTO;
import com.sena.crud_2899747.DTO.responseDTO;
import com.sena.crud_2899747.model.professor;
import com.sena.crud_2899747.service.professorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/professor")
public class professorController {

    @Autowired
    private professorService professorService;

    // POST: Registrar un nuevo profesor
    @PostMapping("/")
    public ResponseEntity<Object> registerProfessor(@RequestBody professorDTO professor) {
        responseDTO response = professorService.save(professor);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // GET: Obtener la lista de todos los profesores
    @GetMapping("/")
    public ResponseEntity<Object> getAllProfessors() {
        List<professor> professorList = professorService.findAll();
        return new ResponseEntity<>(professorList, HttpStatus.OK);
    }

    // GET: Obtener un profesor por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getProfessorById(@PathVariable int id) {
        Optional<professor> professorOpt = professorService.findById(id);
        if (!professorOpt.isPresent()) {
            return new ResponseEntity<>(
                    new responseDTO(HttpStatus.NOT_FOUND.toString(), "Profesor no encontrado"),
                    HttpStatus.NOT_FOUND
            );
        }
        return new ResponseEntity<>(professorOpt.get(), HttpStatus.OK);
    }

    // PUT: Actualizar la información de un profesor existente
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProfessor(@PathVariable int id, @RequestBody professorDTO professor) {
        responseDTO response = professorService.updateProfessor(id, professor);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // DELETE: Eliminar un profesor por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProfessor(@PathVariable int id) {
        responseDTO response = professorService.deleteProfessor(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
