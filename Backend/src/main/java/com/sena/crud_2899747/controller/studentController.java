package com.sena.crud_2899747.controller;


import com.sena.crud_2899747.DTO.studentDTO;

import com.sena.crud_2899747.service.studentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/student")
public class studentController {

    @Autowired
    private studentService studentService;

    @PostMapping("/")
    public ResponseEntity<Object> registerstudent(@RequestBody studentDTO student) {
        studentService.save(student);
        return new ResponseEntity<>("register OK", HttpStatus.OK);
    }
}
