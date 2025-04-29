package com.sena.crud_2899747.controller;

import org.springframework.web.bind.annotation.RestController;
import com.sena.crud_2899747.DTO.departmentDTO;
import com.sena.crud_2899747.DTO.responseDTO;
import com.sena.crud_2899747.service.departmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/department")
public class departmentController {

    /*
     * Endpoints para:
     * - GET (todos, por ID y por filtro)
     * - POST (registro)
     * - PUT (actualización)
     * - DELETE (eliminación)
     */
    @Autowired
    private departmentService departmentService;

    // POST: Registrar un nuevo departamento
    @PostMapping("/")
    public ResponseEntity<Object> registerDepartment(@RequestBody departmentDTO department) {
        responseDTO respuesta = departmentService.save(department);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    // GET: Obtener la lista de todos los departamentos
    @GetMapping("/")
    public ResponseEntity<Object> getAllDepartments() {
        var listaDepartments = departmentService.findAll();
        return new ResponseEntity<>(listaDepartments, HttpStatus.OK);
    }
    
    // GET: Obtener un departamento por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneDepartment(@PathVariable int id) {
        var department = departmentService.findById(id);
        if (!department.isPresent()) {
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(department, HttpStatus.OK);
    }
    
    // GET: Obtener departamentos filtrando por nombre u otro criterio
    @GetMapping("/filter/{filter}")
    public ResponseEntity<Object> getListDepartmentForName(@PathVariable String filter) {
        var departmentList = departmentService.getListDepartmentForName(filter);
        return new ResponseEntity<>(departmentList, HttpStatus.OK);
    }
    
    // PUT: Actualizar la información de un departamento existente
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateDepartment(@PathVariable int id, @RequestBody departmentDTO dto) {
        responseDTO respuesta = departmentService.updateDepartment(id, dto);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    // DELETE: Eliminar un departamento por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteDepartment(@PathVariable int id) {
        responseDTO respuesta = departmentService.deleteDepartment(id);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
