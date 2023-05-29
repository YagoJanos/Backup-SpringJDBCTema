package com.ilegra.university.mapper;

import com.ilegra.university.dto.output.SubjectEnrollmentOutputDTO;
import com.ilegra.university.model.SubjectEnrollment;

public class SubjectEnrollmentMapper {

    public static SubjectEnrollmentOutputDTO convertToDTO(SubjectEnrollment subjectEnrollment) {
        return new SubjectEnrollmentOutputDTO(subjectEnrollment.getEnrollmentId(), subjectEnrollment.getStudentId(), subjectEnrollment.getSubjectId(), subjectEnrollment.getScore(), subjectEnrollment.getEnrollTime(), subjectEnrollment.getDropTime(), subjectEnrollment.getActive());
    }
}
