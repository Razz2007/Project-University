package com.sena.crud_2899747.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sena.crud_2899747.DTO.UserDTO;
import com.sena.crud_2899747.DTO.responseDTO;
import com.sena.crud_2899747.service.UserService;



@RestController
@RequestMapping("api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/") // solo administrador
    public ResponseEntity<Object> getAllUsers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

  
    @GetMapping("profile")
    public ResponseEntity<Object> profile(@AuthenticationPrincipal UserDetails userDetails) {
            
        return new ResponseEntity<Object> (userDetails,HttpStatus.OK);
    }

    @DeleteMapping("/{id}") //solo administrador, falta desarrollar
    public ResponseEntity<Object> deleteUser(@PathVariable int id) {
        responseDTO response = userService.deleteUser(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    
    // @DeleteMapping("/{id}") //usuario, falta desarrollar
    // public ResponseEntity<Object> deleteUser(@PathVariable int id) {
    //     ResponsesDTO response = userService.deleteUser(id);
    //     return new ResponseEntity<>(response, HttpStatus.OK);
    // }

    @PutMapping("/updateProfileUser")//falta
    public ResponseEntity<Object> updateUser(@PathVariable int id, @RequestBody UserDTO userDTO) {
        responseDTO response = userService.updateUser(id, userDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
