package com.sena.crud_2899747.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sena.crud_2899747.model.Roles;


public class UserDTO{
    @JsonProperty("id")
    private int userid; 
    private String username;
    private String password;
    private String email;
    private Roles role;
    private boolean enabled;

    public UserDTO() {
    }

    public UserDTO(int userid, String username, String password, String email, Roles role, boolean enabled) {
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.enabled = enabled;
    }

    public int getUserid() {
        return userid;
    }
    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

     public Roles getRole() {
        return role;
    }
    public void setRole(Roles role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
