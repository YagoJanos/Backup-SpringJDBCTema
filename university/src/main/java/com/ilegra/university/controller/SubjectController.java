package com.ilegra.university.controller;

import com.ilegra.university.dto.input.SubjectInputDTO;
import com.ilegra.university.dto.output.SubjectOutputDTO;
import com.ilegra.university.exception.ResourceNotFoundException;
import com.ilegra.university.mapper.SubjectMapper;
import com.ilegra.university.service.SubjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/subjects")
public class SubjectController {

    private final SubjectService subjectService;


    public SubjectController(SubjectService subjectService){
        this.subjectService = subjectService;
    }



    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registerSubject(@Valid @RequestBody SubjectInputDTO subjectInputDTO){
        subjectService.registerSubject(SubjectMapper.convertToEntity(null, subjectInputDTO), subjectInputDTO.getProgramIds());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateSubject(@PathVariable int id, @Valid @RequestBody SubjectInputDTO subjectInputDTO){
        subjectService.updateSubject(SubjectMapper.convertToEntity(id, subjectInputDTO), subjectInputDTO.getProgramIds());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSubject(@PathVariable int id){
        subjectService.deleteSubject(id);
    }



    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SubjectOutputDTO findSubjectById(@PathVariable int id){
        return SubjectMapper.convertToDTO(subjectService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Subject with id " + id + " not found.")));
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public SubjectOutputDTO findSubjectByName(@RequestParam String name) {
        return SubjectMapper.convertToDTO(subjectService.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Subject with name " + name + " not found.")));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SubjectOutputDTO> findAllSubjects() {
        return subjectService.findAll().stream().map(SubjectMapper::convertToDTO).collect(Collectors.toUnmodifiableList());
    }
}

