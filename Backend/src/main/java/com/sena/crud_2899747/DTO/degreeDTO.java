package com.sena.crud_2899747.DTO;

public class degreeDTO {

    private String name;

    private int duration_years;

    private String coordinator;

    private String faculty;


    public degreeDTO(String name, int duration_years, String coordinator, String faculty) {
        this.name = name;
        this.coordinator = coordinator;
        this.faculty = faculty;
        this.duration_years = duration_years;
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
}
