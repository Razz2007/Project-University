package com.sena.crud_2899747.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sena.crud_2899747.DTO.RequestLoginDTO;
import com.sena.crud_2899747.DTO.RequestRegisterUserDTO;
import com.sena.crud_2899747.DTO.ResponseLogin;
import com.sena.crud_2899747.DTO.responseDTO;
import com.sena.crud_2899747.DTO.UserDTO;
import com.sena.crud_2899747.model.Roles;
import com.sena.crud_2899747.model.Users;
import com.sena.crud_2899747.repository.Iuser;
import com.sena.crud_2899747.service.jwt.jwtServices;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService  {
 
    private final Iuser data;
    private final jwtServices jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    public List<Users> findAll() {
        return data.findAll();
    }

    public Optional<Users> findById(int id) {
        return data.findById(id);
    }

    public Optional<Users> findByUsername(String username) {
        return data.findByUsername(username);
    }

    public Optional<Users> findByEmail(String Email) {
        return data.findByEmail(Email);
    }

    public responseDTO deleteUser(int id) {
        Optional<Users> usuario = findById(id);
        if (!usuario.isPresent()) {
            return new responseDTO(HttpStatus.NOT_FOUND.toString(), "El usuario no existe");
        }

        data.deleteById(id);
        return new responseDTO(HttpStatus.OK.toString(), "Usuario eliminado correctamente");
    }

    

    public responseDTO save(RequestRegisterUserDTO userDTO) {
        Users usuario = convertToModelRegister(userDTO);
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        data.save(usuario);
        return new responseDTO(HttpStatus.OK.toString(), "Usuario guardado correctamente");
    }

   
    public ResponseLogin login(RequestLoginDTO login) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.getUsername(),
                        login.getPassword()));
        UserDetails user=data.findByUsername(login.getUsername()).orElseThrow();
        ResponseLogin response=new ResponseLogin(jwtService.getToken(user));
        return response;

    }

    public responseDTO updateUser(int id, UserDTO userDTO) {
        Optional<Users> usuario = findById(id);
        if (!usuario.isPresent()) {
            return new responseDTO(HttpStatus.NOT_FOUND.toString(), "El usuario no existe");
        }

        Users updatedUser = usuario.get();
        updatedUser.setUsername(userDTO.getUsername());
        updatedUser.setPassword(userDTO.getPassword());
        updatedUser.setEmail(userDTO.getEmail());
        updatedUser.setEnabled(userDTO.isEnabled());
        updatedUser.setRole(userDTO.getRole());

        data.save(updatedUser);
        return new responseDTO(HttpStatus.OK.toString(), "Usuario actualizado correctamente");
    }

    public Users convertToModelRegister(RequestRegisterUserDTO usuario) {
        Roles rol = new Roles();
        // asignamos rol por defecto
        // registrar el rol 1 como usuario estandar
        rol.setRoleid(1);
        return new Users(
                0,
                usuario.getUsername(),
                usuario.getPassword(),
                usuario.getEmail(),
                true,
                rol);
    }

    public Users convertToModel(UserDTO userDTO) {
        Roles rol =new Roles();
        //rol por defecto, recordar registrar en base datos este como rol default
        rol.setRoleid(1);

        return new Users(
                0,
                userDTO.getUsername(),
                userDTO.getPassword(),
                userDTO.getEmail(),
                userDTO.isEnabled(),
                rol);
    }

  
}
