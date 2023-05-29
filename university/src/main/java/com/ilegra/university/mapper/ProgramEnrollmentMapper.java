package com.ilegra.university.mapper;

import com.ilegra.university.dto.output.ProgramEnrollmentOutputDTO;
import com.ilegra.university.model.ProgramEnrollment;

public class ProgramEnrollmentMapper {

    public static ProgramEnrollmentOutputDTO convertToDTO(ProgramEnrollment programEnrollment) {
        return new ProgramEnrollmentOutputDTO(programEnrollment.getEnrollmentId(), programEnrollment.getStudentId(), programEnrollment.getProgramId(), programEnrollment.getEnrollTime(), programEnrollment.getDropTime(), programEnrollment.getActive());
    }
}
