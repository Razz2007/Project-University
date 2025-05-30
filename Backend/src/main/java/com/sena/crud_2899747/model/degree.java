package com.sena.crud_2899747.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
/*Agreganos la notacion entity
 * para indicar que la clase es una entidad
 * name = "nombre que registra en la base de datos"
 */
@Entity (name = "degree")
public class degree {
    /* 
     * atributos o columnas de la entidad
     * 
     * @ID=es una llave primaria o PK
     * 
     * @Column=columna en la entidad
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name",length = 100, nullable = false)
    private String name;

    @Column(name = "duration_years",length = 11, nullable = false)
    private  int duration_years;

    @Column(name = "coordinator",length = 100, nullable = false)
    private  String coordinator;

    @Column(name = "faculty",length = 100, nullable = false)
    private  String faculty;

    @Column(name="status",nullable =false, columnDefinition = "boolean default true ")
    private boolean status;

    public degree(){}

    public degree(int id, String name, int duration_years, String coordinator, String faculty, boolean status) {
        this.id = id;
        this.name = name;
        this.duration_years = duration_years;
        this.coordinator = coordinator;
        this.faculty = faculty;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration_years() {
        return duration_years;
    }

    public void setDuration_years(int duration_years) {
        this.duration_years = duration_years;
    }

    public String getCoordinator() {
        return coordinator;
    }

    public void setCoordinator(String coordinator) {
        this.coordinator = coordinator;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }
    public boolean getStatus() {
        return status;
     }
  
     public void setStatus(boolean status) {
        this.status = status;
     }
}  

