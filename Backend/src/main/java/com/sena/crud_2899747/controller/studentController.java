package com.sena.crud_2899747.controller;
import org.springframework.web.bind.annotation.RestController;

import com.sena.crud_2899747.DTO.studentDTO;
import com.sena.crud_2899747.service.studentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
    private studentService userService;

    @PostMapping("/")
    public ResponseEntity<Object> registerUser(@RequestBody studentDTO student) {
        userService.save(student);
        return new ResponseEntity<>("register OK", HttpStatus.OK);
    }

}
