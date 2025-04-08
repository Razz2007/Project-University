package com.sena.crud_2899747.controller;

import org.springframework.web.bind.annotation.RestController;

import com.sena.crud_2899747.DTO.departmentDTO;
import com.sena.crud_2899747.DTO.responseDTO;
import com.sena.crud_2899747.service.departmentService;

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
@RequestMapping("/department")
public class departmentController {

    /*
     * GET
     * POST(REGISTER)
     * PUT
     * DELETE
     */
    @Autowired
    private departmentService departmentService;

    @PostMapping("/")
    public ResponseEntity<Object> registerDepartment(@RequestBody departmentDTO department) {
        responseDTO respuesta = departmentService.save(department);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<Object> getAllDepartments() {
        var listaDepartments = departmentService.findAll();
        return new ResponseEntity<>(listaDepartments, HttpStatus.OK);
    }

    /*
     * Se requiere un dato, el ID
     * PathVariable=captura de información por la URL
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneDepartment(@PathVariable int id) {
        var department = departmentService.findById(id);
        if (!department.isPresent())
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteDepartment(@PathVariable int id) {
        var message = departmentService.deleteDepartment(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}