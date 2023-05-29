package com.ilegra.university.controller;

import com.ilegra.university.dto.input.ProfessorInputDTO;
import com.ilegra.university.dto.output.ProfessorOutputDTO;
import com.ilegra.university.exception.ResourceNotFoundException;
import com.ilegra.university.mapper.ProfessorMapper;
import com.ilegra.university.service.ProfessorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/professors")
public class ProfessorController {


    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService){
        this.professorService = professorService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registerProfessor(@Valid @RequestBody ProfessorInputDTO professorInputDTO){
        professorService.registerProfessor(ProfessorMapper.convertToEntity(null, professorInputDTO), professorInputDTO.getSubjectEntityId());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProfessor(@PathVariable int id, @Valid @RequestBody ProfessorInputDTO professorInputDTO){
        professorService.updateProfessor(ProfessorMapper.convertToEntity(id, professorInputDTO));
    }



    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProfessorOutputDTO findProfessorById(@PathVariable int id){
        return ProfessorMapper.convertToDTO(professorService.findProfessorById(id).orElseThrow(() -> new ResourceNotFoundException("Professor with id " + id + " not found.")));
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<ProfessorOutputDTO> findProfessorsByName(@RequestParam String name, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "0") int page) {
        return professorService.findProfessorsByName(name, pageSize, page).stream().map(ProfessorMapper::convertToDTO).collect(Collectors.toUnmodifiableList());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProfessorOutputDTO> findAllProfessors(@RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "0") int page) {
        return professorService.findAllProfessors(pageSize, page).stream().map(ProfessorMapper::convertToDTO).collect(Collectors.toUnmodifiableList());
    }
}
