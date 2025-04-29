package com.sena.crud_2899747.DTO;

public class courseDTO {
    private int courseId;
    private String name;
    private int credits;
    private String level;
    private String prerequisites;
    private boolean status;

    // Constructor vacío
    public courseDTO() {
    }

    // Constructor con parámetros
    public courseDTO(int courseId, String name, int credits, String level, String prerequisites, boolean status) {
        this.courseId = courseId;
        this.name = name;
        this.credits = credits;
        this.level = level;
        this.prerequisites = prerequisites;
        this.status = status;
    }

    // Getters y Setters
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(String prerequisites) {
        this.prerequisites = prerequisites;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
