package com.ilegra.university.controller;

import com.ilegra.university.dto.input.StudentInputDTO;
import com.ilegra.university.dto.output.StudentOutputDTO;
import com.ilegra.university.exception.ResourceNotFoundException;
import com.ilegra.university.mapper.StudentMapper;
import com.ilegra.university.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {


    private final StudentService studentService;

    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registerStudent(@Valid @RequestBody StudentInputDTO studentInputDTO){
        studentService.registerStudent(StudentMapper.convertToEntity(null, studentInputDTO));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStudent(@PathVariable int id, @Valid @RequestBody StudentInputDTO studentInputDTO){
        studentService.updateStudent(StudentMapper.convertToEntity(id, studentInputDTO));
    }



    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StudentOutputDTO findStudentById(@PathVariable int id){
        return StudentMapper.convertToDTO(studentService.findStudentById(id).orElseThrow(() -> new ResourceNotFoundException("Student with id " + id + " not found.")));
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentOutputDTO> findStudentsByName(@RequestParam String name, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "0") int page) {
        return studentService.findStudentsByName(name, pageSize, page).stream().map(StudentMapper::convertToDTO).collect(Collectors.toUnmodifiableList());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StudentOutputDTO> findAllStudents(@RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "0") int page) {
        return studentService.findAllStudents(pageSize, page).stream().map(StudentMapper::convertToDTO).collect(Collectors.toUnmodifiableList());
    }
}
