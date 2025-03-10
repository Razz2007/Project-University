package com.sena.crud_2899747.DTO;

public class degreeDTO {
    private int degreeId;
    private String name;
    private int durationYears;
    private String coordinator;
    private String faculty;

    public degreeDTO() {}

    public degreeDTO(int degreeId, String name, int durationYears, String coordinator, String faculty) {
        this.degreeId = degreeId;
        this.name = name;
        this.durationYears = durationYears;
        this.coordinator = coordinator;
        this.faculty = faculty;
    }

    public int getDegreeId() {
        return degreeId;
    }

    public void setDegreeId(int degreeId) {
        this.degreeId = degreeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDurationYears() {
        return durationYears;
    }

    public void setDurationYears(int durationYears) {
        this.durationYears = durationYears;
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
