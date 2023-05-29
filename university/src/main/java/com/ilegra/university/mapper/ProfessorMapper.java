package com.ilegra.university.mapper;

import com.ilegra.university.dto.input.ProfessorInputDTO;
import com.ilegra.university.dto.output.ProfessorOutputDTO;
import com.ilegra.university.model.Professor;

public class ProfessorMapper {

    public static Professor convertToEntity(Integer id, ProfessorInputDTO professorInputDTO) {
        Professor.Builder professorBuilder = Professor.newBuilder()
                .withName(professorInputDTO.getName())
                .withEmail(professorInputDTO.getEmail())
                .withPhone(professorInputDTO.getPhone())
                .withCpf(professorInputDTO.getCpf());

        if (id != null) {
            professorBuilder.withId(id);
        }

        return professorBuilder.build();
    }

    public static ProfessorOutputDTO convertToDTO(Professor professor) {
        return new ProfessorOutputDTO(professor.getId(), professor.getName(), professor.getEmail(), professor.getPhone(), professor.getCpf());
    }
}
