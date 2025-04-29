package com.sena.crud_2899747.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.sena.crud_2899747.DTO.responseDTO;
import com.sena.crud_2899747.DTO.libraryDTO;
import com.sena.crud_2899747.model.library;
import com.sena.crud_2899747.service.libraryService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/library")
public class libraryController {

    @Autowired
    private libraryService libraryService;

    // Registra una nueva biblioteca
    @PostMapping("/")
    public ResponseEntity<Object> registerLibrary(@RequestBody libraryDTO library) {
        responseDTO respuesta = libraryService.saveLibrary(library);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    // Obtiene la lista de todas las bibliotecas
    @GetMapping("/")
    public ResponseEntity<Object> getAllLibraries() {
        List<library> listaLibraries = libraryService.findAll();
        return new ResponseEntity<>(listaLibraries, HttpStatus.OK);
    }

    // Obtiene una biblioteca por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneLibrary(@PathVariable int id) {
        Optional<library> libraryOpt = libraryService.findById(id);
        if (!libraryOpt.isPresent()) {
            responseDTO respuesta = new responseDTO(
                    HttpStatus.NOT_FOUND.toString(),
                    "Error: La biblioteca con ID " + id + " no existe"
            );
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(libraryOpt.get(), HttpStatus.OK);
    }

    // Elimina una biblioteca por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteLibrary(@PathVariable int id) {
        responseDTO respuesta = libraryService.deleteLibrary(id);
        if (respuesta.getStatus().equals(HttpStatus.NOT_FOUND.toString())) {
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    // Actualiza los datos de una biblioteca existente
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateLibrary(@PathVariable int id, @RequestBody libraryDTO dto) {
        responseDTO respuesta = libraryService.updateLibrary(id, dto);
        if (respuesta.getStatus().equals(HttpStatus.NOT_FOUND.toString()) ||
                respuesta.getStatus().equals(HttpStatus.BAD_REQUEST.toString())) {
            HttpStatus status = respuesta.getStatus().equals(HttpStatus.NOT_FOUND.toString())
                    ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(respuesta, status);
        }
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
