package com.ilegra.university.dto.input;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProgramInputDTO {

    @NotBlank
    @Size(max=255)
    private String name;
    @NotNull
    @Min(1)
    private Integer degreeId;
    @NotNull
    @Min(1)
    private Short totalSemesters;






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
}
