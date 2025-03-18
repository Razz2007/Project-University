package com.sena.crud_2899747.controller;

import org.springframework.web.bind.annotation.RestController;

import com.sena.crud_2899747.DTO.classroomDTO;
import com.sena.crud_2899747.DTO.responseDTO;
import com.sena.crud_2899747.service.classroomService;
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
@RequestMapping("/classroom")
public class classroomController {

    /*
     * GET
     * POST(REGISTER)
     * PUT
     * DELETE
     */
    @Autowired
    private classroomService classroomService;

    @PostMapping("/")
    public ResponseEntity<Object> registerClassroom(@RequestBody classroomDTO classroom) {
        responseDTO respuesta = classroomService.save(classroom);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<Object> getAllClassrooms() {
        var listaClassrooms = classroomService.findAll();
        return new ResponseEntity<>(listaClassrooms, HttpStatus.OK);
    }

    /*
     * Se requiere un dato, el ID
     * PathVariable=captura de información por la URL
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneClassroom(@PathVariable int id) {
        var classroom = classroomService.findById(id);
        if (!classroom.isPresent())
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(classroom, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteClassroom(@PathVariable int id) {
        var message = classroomService.deleteClassroom(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}