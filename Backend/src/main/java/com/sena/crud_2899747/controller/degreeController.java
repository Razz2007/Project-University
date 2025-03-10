package com.sena.crud_2899747.controller;

import org.springframework.web.bind.annotation.RestController;
import com.sena.crud_2899747.DTO.degreeDTO;
import com.sena.crud_2899747.service.degreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/degree")
public class degreeController {

    @Autowired
    private degreeService degreeService;

    @PostMapping
    public ResponseEntity<String> registerDegree(@RequestBody degreeDTO degree) {
        degreeService.save(degree);
        return new ResponseEntity<>("Degree registered successfully", HttpStatus.CREATED);
    }
}
