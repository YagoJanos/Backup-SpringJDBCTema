package com.ilegra.university.mapper;

import com.ilegra.university.dto.input.DegreeInputDTO;
import com.ilegra.university.dto.output.DegreeOutputDTO;
import com.ilegra.university.model.Degree;

public class DegreeMapper {

    public static Degree convertToEntity(Integer id, DegreeInputDTO degreeInputDTO) {
        Degree.Builder degreeBuilder = Degree.newBuilder()
                .withName(degreeInputDTO.getName());

        if (id != null) {
            degreeBuilder.withId(id);
        }

        return degreeBuilder.build();
    }

    public static DegreeOutputDTO convertToDTO(Degree degree) {
        return new DegreeOutputDTO(degree.getId(), degree.getName());
    }
}
