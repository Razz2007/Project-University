package com.sena.crud_2899747.DTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sena.crud_2899747.model.Pages;
import com.sena.crud_2899747.model.Roles;


public class Permission_roleDTO {
    @JsonProperty("id")
    private int permission_roleid;
    private Pages page;
    private Roles role;
    private String type;

    public Permission_roleDTO() {
    }

    public Permission_roleDTO(int permission_roleid, Pages page, Roles role, String type) {
        this.permission_roleid = permission_roleid;
        this.page = page;
        this.role = role;
        this.type = type;
    }

    public int getPermission_roleid() {
        return permission_roleid;
    }
    public void setPermission_roleid(int permission_roleid) {
        this.permission_roleid = permission_roleid;
    }

    public Pages getPage() {
        return page;
    }
    public void setPage(Pages page) {
        this.page = page;
    }

    public Roles getRole() {
        return role;
    }
    public void setRole(Roles role) {
        this.role = role;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

}
