package com.ilegra.university.controller;

import com.ilegra.university.dto.input.ClassroomInputDTO;
import com.ilegra.university.dto.output.ClassroomOutputDTO;
import com.ilegra.university.exception.ResourceNotFoundException;
import com.ilegra.university.mapper.ClassroomMapper;
import com.ilegra.university.service.ClassroomService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/classrooms")
public class ClassroomController {

    private final ClassroomService classroomService;

    public ClassroomController(ClassroomService classroomService){
        this.classroomService = classroomService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registerClassroom(@Valid @RequestBody ClassroomInputDTO classroomInputDTO){
        classroomService.registerClassroom(ClassroomMapper.convertToEntity(null, classroomInputDTO));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateClassroom(@PathVariable int id, @Valid @RequestBody ClassroomInputDTO classroomInputDTO){
        classroomService.updateClassroom(ClassroomMapper.convertToEntity(id, classroomInputDTO));
    }



    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClassroomOutputDTO findClassroomById(@PathVariable int id){
        return ClassroomMapper.convertToDTO(classroomService.findClassroomById(id).orElseThrow(() -> new ResourceNotFoundException("Classroom with id " + id + " not found.")));
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ClassroomOutputDTO findClassroomsByName(@RequestParam String name) {
        return ClassroomMapper.convertToDTO(classroomService.findClassroomByName(name).orElseThrow(() -> new ResourceNotFoundException("Classroom with name " + name + " not found.")));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ClassroomOutputDTO> findAllClassrooms() {
        return classroomService.findAllClassrooms().stream().map(ClassroomMapper::convertToDTO).collect(Collectors.toUnmodifiableList());
    }
}
