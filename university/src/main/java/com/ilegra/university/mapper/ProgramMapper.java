package com.ilegra.university.mapper;

import com.ilegra.university.dto.input.ProgramInputDTO;
import com.ilegra.university.dto.output.ProgramOutputDTO;
import com.ilegra.university.model.Program;

public class ProgramMapper {

    public static Program convertToEntity(Integer id, ProgramInputDTO programInputDTO) {

        Program.Builder programBuilder = Program.newBuilder()
                .withName(programInputDTO.getName())
                .withDegreeId(programInputDTO.getDegreeId())
                .withTotalSemesters(programInputDTO.getTotalSemesters());

        if (id != null) {
            programBuilder.withId(id);
        }

        return programBuilder.build();
    }

    public static ProgramOutputDTO convertToDTO(Program program) {

        return new ProgramOutputDTO(program.getId(), program.getName(), program.getDegreeId(), program.getTotalSemesters(), program.isActive());
    }
}
