package com.sena.crud_2899747.controller;

import org.springframework.web.bind.annotation.RestController;
import com.sena.crud_2899747.DTO.classroomDTO;
import com.sena.crud_2899747.DTO.responseDTO;
import com.sena.crud_2899747.service.classroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/classroom")
public class classroomController {

    /*
     * GET
     * POST (REGISTER)
     * PUT (UPDATE)
     * DELETE
     */

    @Autowired
    private classroomService classroomService;

    // POST: Registrar una nueva aula
    @PostMapping("/")
    public ResponseEntity<Object> registerClassroom(@RequestBody classroomDTO classroom) {
        responseDTO respuesta = classroomService.save(classroom);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    // GET: Obtener la lista de todas las aulas
    @GetMapping("/")
    public ResponseEntity<Object> getAllClassrooms() {
        var listaClassrooms = classroomService.findAll();
        return new ResponseEntity<>(listaClassrooms, HttpStatus.OK);
    }

    // GET: Obtener una aula por su ID. Se captura la información mediante PathVariable
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneClassroom(@PathVariable int id) {
        var classroom = classroomService.findById(id);
        if (!classroom.isPresent())
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(classroom, HttpStatus.OK);
    }
    
    // GET: Obtener la lista de aulas filtradas por nombre (u otro criterio)
    @GetMapping("/filter/{filter}")
    public ResponseEntity<Object> getListClassroomForName(@PathVariable String filter) {
        var classroomList = classroomService.getListClassroomForName(filter);
        return new ResponseEntity<>(classroomList, HttpStatus.OK);
    }

    // PUT: Actualizar la información de un aula existente
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateClassroom(@PathVariable int id, @RequestBody classroomDTO dto) {
        responseDTO respuesta = classroomService.updateClassroom(id, dto);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    // DELETE: Eliminar un aula por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteClassroom(@PathVariable int id) {
        responseDTO respuesta = classroomService.deleteClassroom(id);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
