package com.sena.crud_2899747.controller;

import org.springframework.web.bind.annotation.RestController;

import com.sena.crud_2899747.DTO.responseDTO;
import com.sena.crud_2899747.DTO.degreeDTO;
import com.sena.crud_2899747.model.degree;
import com.sena.crud_2899747.service.degreeService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1/degree")
public class degreeController {

    /*
     * GET
     * POST(REGISTER)
     * PUT
     * DELETE
     */
    @Autowired
    private degreeService degreeService;

    @PostMapping("/")
    public ResponseEntity<Object> registerDegree(@RequestBody degreeDTO degree) {
        responseDTO respuesta = degreeService.save(degree);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<Object> getAllDegrees() {
        var listaDegrees = degreeService.findAll();
        return new ResponseEntity<>(listaDegrees, HttpStatus.OK);
    }

    /*
     * Se requiere un dato, el ID
     * PathVariable=captura de información por la URL
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneDegree(@PathVariable int id) {
        var degree = degreeService.findById(id);
        if (!degree.isPresent())
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(degree, HttpStatus.OK);
    }

    @GetMapping("/filter/{filter}")
    public ResponseEntity<Object> getListDegreeForName(@PathVariable String filter) {
        var degreeList = degreeService.getListDegreeForName(filter);
        return new ResponseEntity<>(degreeList, HttpStatus.OK);
    }   

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteDegree(@PathVariable int id) {
        var message = degreeService.deleteDegree(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}