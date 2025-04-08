package com.sena.crud_2899747.controller;

import org.springframework.web.bind.annotation.RestController;
import com.sena.crud_2899747.DTO.scheduleDTO;
import com.sena.crud_2899747.service.scheduleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/schedule")
public class scheduleController {

    @Autowired
    private scheduleService scheduleService;

    @PostMapping
    public ResponseEntity<String> registerSchedule(@RequestBody scheduleDTO schedule) {
        scheduleService.save(schedule);
        return new ResponseEntity<>("Schedule registered successfully", HttpStatus.CREATED);
    }
}

