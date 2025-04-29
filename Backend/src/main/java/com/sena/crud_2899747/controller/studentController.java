package com.sena.crud_2899747.controller;

import org.springframework.web.bind.annotation.RestController;

import com.sena.crud_2899747.DTO.responseDTO;
import com.sena.crud_2899747.DTO.studentDTO;
import com.sena.crud_2899747.service.studentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/student")
public class studentController {

    /*
     * GET
     * POST(REGISTER)
     * PUT
     * DELETE
     */
    @Autowired
    private studentService studentService;

    @PostMapping("/")   
    public ResponseEntity<Object> registerStudent(@RequestBody studentDTO student) {
        responseDTO respuesta = studentService.save(student);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<Object> getAllStudents() {
        var listaStudents = studentService.findAll();
        return new ResponseEntity<>(listaStudents, HttpStatus.OK);
    }

    /*
     * Se requiere un dato, el ID
     * PathVariable=captura de información por la URL
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneStudent(@PathVariable int id) {
        Optional<studentDTO> studentOpt = studentService.findById(id);
        if (!studentOpt.isPresent())
            return new ResponseEntity<>(
                    new responseDTO(HttpStatus.NOT_FOUND.toString(), "Estudiante no encontrado"),
                    HttpStatus.NOT_FOUND
            );
        return new ResponseEntity<>(studentOpt.get(), HttpStatus.OK);
    }

    @GetMapping("/filter/{filter}")
    public ResponseEntity<Object> getListStudentForName(@PathVariable String filter) {
        var studentList = studentService.getListStudentForFirstName(filter);
        return new ResponseEntity<>(studentList, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteStudent(@PathVariable int id) {
        responseDTO respuesta = studentService.deleteStudent(id);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateStudent(@PathVariable int id, @RequestBody studentDTO dto) {
        responseDTO respuesta = studentService.updateStudent(id, dto);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
