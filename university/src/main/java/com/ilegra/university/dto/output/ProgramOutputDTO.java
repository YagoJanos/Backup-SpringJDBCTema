package com.ilegra.university.dto.output;

public class ProgramOutputDTO {

    private Integer id;
    private String name;
    private Integer degreeId;
    private Short totalSemesters;
    private boolean active;

    public ProgramOutputDTO(Integer id, String name, Integer degreeId, Short totalSemesters, boolean active) {
        this.id = id;
        this.name = name;
        this.degreeId = degreeId;
        this.totalSemesters = totalSemesters;
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

    public Short getTotalSemesters() {
        return totalSemesters;
    }

    public void setTotalSemesters(Short totalSemesters) {
        this.totalSemesters = totalSemesters;
    }

    public Integer getDegreeId() {
        return degreeId;
    }

    public void setDegreeId(Integer degreeId) {
        this.degreeId = degreeId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
