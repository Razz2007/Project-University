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
    @Column(name = "degree_id")
    private int degree_id;

    @Column(name = "name",length = 100, nullable = false)
    private String name;

    @Column(name = "duration_years",length = 11, nullable = false)
    private  int duration_years;

    @Column(name = "coordinator",length = 100, nullable = false)
    private  String coordinator;

    @Column(name = "faculty",length = 100, nullable = false)
    private  String faculty;

    public degree(int degree_id, String name, int duration_years, String coordinator, String faculty) {
        this.degree_id = degree_id;
        this.name = name;
        this.duration_years = duration_years;
        this.coordinator = coordinator;
        this.faculty = faculty;
    }

    public int getDegree() {
        return degree_id;
    }

    public void setDegree(int degree_id) {
        this.degree_id = degree_id;
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

    public void setDration_years(int duration_years) {
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

}  

