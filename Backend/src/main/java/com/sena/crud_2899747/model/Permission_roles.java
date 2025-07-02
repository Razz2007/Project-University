package com.sena.crud_2899747.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "permission_role")
public class Permission_roles{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permission_roleid")
    private int permission_roleid;

    @ManyToOne
    @JoinColumn(name = "pageid", nullable = false)
    private Pages page;

    @ManyToOne
    @JoinColumn(name = "roleid", nullable = false)
    private Roles role;

    @Column(name = "type", nullable = false)
    private String type;

    public Permission_roles() {
        // Default constructor
    }

    public Permission_roles(int permission_roleid, Pages page, Roles role, String type) {
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

