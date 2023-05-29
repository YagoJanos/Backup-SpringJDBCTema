package com.ilegra.university.dto.output;

import java.util.Date;

public class SubjectOutputDTO {

    private Integer id;
    private String name;
    private Integer hours;
    private Integer classRoomId;
    private Integer professorId;
    private boolean active;

    public SubjectOutputDTO(Integer id, String name, Integer hours, Integer classRoomId, Integer professorId, boolean active) {
        this.id = id;
        this.name = name;
        this.hours = hours;
        this.classRoomId = classRoomId;
        this.professorId = professorId;
        this.active = active;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Integer getClassRoomId() {
        return classRoomId;
    }

    public void setClassRoomId(Integer classRoomId) {
        this.classRoomId = classRoomId;
    }

    public Integer getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Integer professorId) {
        this.professorId = professorId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
