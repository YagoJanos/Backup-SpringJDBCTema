package com.ilegra.university.controller;

import com.ilegra.university.dto.input.ProgramInputDTO;
import com.ilegra.university.dto.output.ProgramOutputDTO;
import com.ilegra.university.exception.ResourceNotFoundException;
import com.ilegra.university.mapper.ProgramMapper;
import com.ilegra.university.model.Program;
import com.ilegra.university.service.ProgramService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/programs")
public class ProgramController {

    private final ProgramService programService;


    public ProgramController(ProgramService programService){
        this.programService = programService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registerProgram(@Valid @RequestBody ProgramInputDTO programInputDTO){
        programService.registerProgram(ProgramMapper.convertToEntity(null, programInputDTO));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProgram(@PathVariable int id, @Valid @RequestBody ProgramInputDTO programInputDTO){
        programService.updateProgram(ProgramMapper.convertToEntity(id, programInputDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProgram(@PathVariable int id){
        programService.deleteProgram(id);
    }



    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProgramOutputDTO findProgramById(@PathVariable int id){
        return ProgramMapper.convertToDTO(programService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Program with id " + id + " not found.")));
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<ProgramOutputDTO> findProgramByName(@RequestParam String name, @RequestParam(required = false) Integer degreeId) {
        List<Program> programs;
        if (degreeId != null){
            programs = new ArrayList<>();
            programService.findByNameAndDegree(name, degreeId).ifPresent(programs::add);
        } else {
            programs = programService.findByName(name);
        }
        return programs.stream()
                .map(ProgramMapper::convertToDTO)
                .collect(Collectors.toUnmodifiableList());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProgramOutputDTO> findAllPrograms() {
        return programService.findAll().stream().map(ProgramMapper::convertToDTO).collect(Collectors.toUnmodifiableList());
    }
}
