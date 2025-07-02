package com.sena.crud_2899747.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sena.crud_2899747.DTO.RequestLoginDTO;
import com.sena.crud_2899747.DTO.RequestRegisterUserDTO;
import com.sena.crud_2899747.DTO.ResponseLogin;
import com.sena.crud_2899747.DTO.responseDTO;
import com.sena.crud_2899747.service.UserService;



@RestController
@RequestMapping("api/v1/public/users")
public class UserPublicController {
     @Autowired
    private UserService userService;

   @PostMapping("/register")
    public ResponseEntity<Object> saveUser(@RequestBody RequestRegisterUserDTO user) {
        responseDTO response = userService.save(user);
        // ResponsesDTO response =null;
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login") //falta desarrollar
    public ResponseEntity<ResponseLogin> login(@RequestBody RequestLoginDTO userDTO) {
        ResponseLogin response = userService.login(userDTO);
        // ResponseLogin response = null;
        return new ResponseEntity<ResponseLogin>(response, HttpStatus.OK);
    }

    //  @PostMapping("/forgot") //falta desarrollar
    // public ResponseEntity<Object> forgot(@RequestBody UserDTO userDTO) {
    //     // ResponsesDTO response = userService.save(userDTO);
    //     return new ResponseEntity<>(response, HttpStatus.OK);
    // }
    
}
