package com.sena.crud_2899747.controller;

import org.springframework.web.bind.annotation.RestController;

import com.sena.crud_2899747.DTO.responseDTO;
import com.sena.crud_2899747.DTO.courseDTO;
import com.sena.crud_2899747.service.courseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1/course")
public class courseController {

    /*
     * GET
     * POST(REGISTER)
     * PUT
     * DELETE
     */
    @Autowired
    private courseService courseService;

    @PostMapping("/")
    public ResponseEntity<Object> registerCourse(@RequestBody courseDTO course) {
        responseDTO respuesta = courseService.save(course);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<Object> getAllCourses() {
        var listaCourses = courseService.findAll();
        return new ResponseEntity<>(listaCourses, HttpStatus.OK);
    }

    /*
     * Se requiere un dato, el ID
     * PathVariable=captura de información por la URL
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneCourse(@PathVariable int id) {
        var course = courseService.findById(id);
        if (!course.isPresent())
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @GetMapping("/filter/{filter}")
    public ResponseEntity<Object> getListCourseForName(@PathVariable String filter) {
        var courseList = courseService.getListCourseForName(filter);
        return new ResponseEntity<>(courseList, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCourse(@PathVariable int id) {
        responseDTO respuesta = courseService.deleteCourse(id);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCourse(@PathVariable int id, @RequestBody courseDTO dto) {
        responseDTO respuesta = courseService.updateCourse(id, dto);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
