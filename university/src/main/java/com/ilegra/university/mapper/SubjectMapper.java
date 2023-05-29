package com.ilegra.university.mapper;

import com.ilegra.university.dto.input.SubjectInputDTO;
import com.ilegra.university.dto.output.SubjectOutputDTO;
import com.ilegra.university.model.Subject;

public class SubjectMapper {

    public static Subject convertToEntity(Integer id, SubjectInputDTO subjectInputDTO) {

        Subject.Builder subjectBuilder = Subject.newBuilder()
                .withName(subjectInputDTO.getName())
                .withHours(subjectInputDTO.getHours());

        if (id != null) {
            subjectBuilder.withId(id);
        }

        return subjectBuilder.build();
    }

    public static SubjectOutputDTO convertToDTO(Subject subject) {
        return new SubjectOutputDTO(subject.getId(), subject.getName(), subject.getHours(), subject.getClassRoomId(), subject.getProfessorId(), subject.isActive());
    }
}
