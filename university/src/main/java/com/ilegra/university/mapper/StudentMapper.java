package com.ilegra.university.mapper;

import com.ilegra.university.dto.input.StudentInputDTO;
import com.ilegra.university.dto.output.StudentOutputDTO;
import com.ilegra.university.model.Student;

public class StudentMapper {

    public static Student convertToEntity(Integer id, StudentInputDTO studentInputDTO) {
        Student.Builder studentBuilder = Student.newBuilder()
                .withName(studentInputDTO.getName())
                .withEmail(studentInputDTO.getEmail())
                .withPhone(studentInputDTO.getPhone())
                .withCpf(studentInputDTO.getCpf());

        if (id != null) {
            studentBuilder.withId(id);
        }

        return studentBuilder.build();
    }

    public static StudentOutputDTO convertToDTO(Student student) {
        return new StudentOutputDTO(student.getId(), student.getName(), student.getEmail(), student.getPhone(), student.getCpf());
    }
}
