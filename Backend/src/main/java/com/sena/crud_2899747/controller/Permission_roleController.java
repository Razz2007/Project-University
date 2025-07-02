package com.sena.crud_2899747.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sena.crud_2899747.DTO.Permission_roleDTO;
import com.sena.crud_2899747.DTO.responseDTO;
import com.sena.crud_2899747.model.Permission_roles;
import com.sena.crud_2899747.service.Permission_roleService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/permission_role")
public class Permission_roleController {
    @Autowired
    private Permission_roleService permissionRoleService;

    @GetMapping("/")
    public ResponseEntity<List<Permission_roles>> getAllPermissionRoles() {
        List<Permission_roles> permissionRoles = permissionRoleService.findAll();
        return new ResponseEntity<>(permissionRoles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPermissionRoleById(@PathVariable int id) {
        Optional<Permission_roles> permissionRole = permissionRoleService.findById(id);
        if (!permissionRole.isPresent()) {
            return new ResponseEntity<>("Permiso de rol no encontrado", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(permissionRole.get(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Object> createPermissionRole(@RequestBody Permission_roleDTO permissionRoleDTO) {
       responseDTO response = permissionRoleService.save(permissionRoleDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePermissionRole(@PathVariable int id, @RequestBody Permission_roleDTO permissionRoleDTO) {
       responseDTO response = permissionRoleService.updatePermissionRole(id, permissionRoleDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePermissionRole(@PathVariable int id) {
       responseDTO response = permissionRoleService.deletePermissionRole(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}