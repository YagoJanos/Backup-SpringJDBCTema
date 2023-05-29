package com.ilegra.university.mapper;

import com.ilegra.university.dto.input.ClassroomInputDTO;
import com.ilegra.university.dto.output.ClassroomOutputDTO;
import com.ilegra.university.model.Classroom;

public class ClassroomMapper {

    public static Classroom convertToEntity(Integer id, ClassroomInputDTO classroomInputDTO) {
        Classroom.Builder classroomBuilder = Classroom.newBuilder()
                .withName(classroomInputDTO.getName());

        if (id != null) {
            classroomBuilder.withId(id);
        }

        return classroomBuilder.build();
    }

    public static ClassroomOutputDTO convertToDTO(Classroom classroom) {
        return new ClassroomOutputDTO(classroom.getId(), classroom.getName());
    }
}
