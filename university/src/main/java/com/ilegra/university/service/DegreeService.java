package com.ilegra.university.service;

import com.ilegra.university.exception.EntityAlreadyExistsException;
import com.ilegra.university.model.Degree;
import com.ilegra.university.repository.degree.DegreeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DegreeService {

    private final DegreeRepository degreeRepository;

    public DegreeService(DegreeRepository degreeRepository){
        this.degreeRepository = degreeRepository;
    }


    public void registerDegree(Degree degree){
        ensureDegreeIsUnique(degree.getName());
        degreeRepository.save(degree);
    }

    public void updateDegree(Degree degree) {
        ensureNoOtherDegreeWithThisNameExist(degree.getName(), degree.getId());
        degreeRepository.update(degree);
    }



    public Optional<Degree> findDegreeById(int id){

        return degreeRepository.findById(id);
    }

    public Optional<Degree> findDegreeByName(String name) {

        return degreeRepository.findByName(name);
    }

    public List<Degree> findAllDegrees() {

        return degreeRepository.findAll();
    }



    private void ensureDegreeIsUnique(String name){
        findDegreeByName(name).ifPresent(degree -> {throw new EntityAlreadyExistsException("A degree with name " + name + " already exists");});
    }

    private void ensureNoOtherDegreeWithThisNameExist(String name, int id) {
        Optional<Degree> degreeOptional = findDegreeByName(name);
        if(degreeOptional.isPresent() && degreeOptional.get().getId() != id){
            throw new EntityAlreadyExistsException("A degree with name " + name + " already exists with a different id");
        }
    }
}
