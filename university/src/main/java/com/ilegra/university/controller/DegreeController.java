package com.ilegra.university.controller;

import com.ilegra.university.dto.input.DegreeInputDTO;
import com.ilegra.university.dto.output.DegreeOutputDTO;
import com.ilegra.university.exception.ResourceNotFoundException;
import com.ilegra.university.mapper.DegreeMapper;
import com.ilegra.university.service.DegreeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/degrees")
public class DegreeController {

    private final DegreeService degreeService;

    public DegreeController(DegreeService degreeService){
        this.degreeService = degreeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registerDegree(@Valid @RequestBody DegreeInputDTO degreeInputDTO){
        degreeService.registerDegree(DegreeMapper.convertToEntity(null, degreeInputDTO));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateDegree(@PathVariable int id, @Valid @RequestBody DegreeInputDTO degreeInputDTO){
        degreeService.updateDegree(DegreeMapper.convertToEntity(id, degreeInputDTO));
    }



    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DegreeOutputDTO findDegreeById(@PathVariable int id){
        return DegreeMapper.convertToDTO(degreeService.findDegreeById(id).orElseThrow(() -> new ResourceNotFoundException("Degree with id " + id + " not found.")));
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public DegreeOutputDTO findDegreesByName(@RequestParam String name) {
        return DegreeMapper.convertToDTO(degreeService.findDegreeByName(name).orElseThrow(() -> new ResourceNotFoundException("Degree with name " + name + " not found.")));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<DegreeOutputDTO> findAllDegrees() {
        return degreeService.findAllDegrees().stream().map(DegreeMapper::convertToDTO).collect(Collectors.toUnmodifiableList());
    }
}
