package com.sena.crud_2899747.DTO;

import java.time.LocalTime;

public class scheduleDTO {

    private int scheduleId;
    private int classroomId;
    private int courseId;
    private String dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    public scheduleDTO() {
    }

    public scheduleDTO(int scheduleId, String dayOfWeek, LocalTime startTime, LocalTime endTime, int classroomId,int courseId) {
        this.scheduleId = scheduleId;
        this.classroomId = classroomId;
        this.courseId = courseId;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }


    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(int classroomId) {
        this.classroomId = classroomId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
