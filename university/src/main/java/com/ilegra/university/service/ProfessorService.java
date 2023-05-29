package com.ilegra.university.service;

import com.ilegra.university.exception.OperationConflictException;
import com.ilegra.university.model.Professor;
import com.ilegra.university.model.Subject;
import com.ilegra.university.repository.professor.ProfessorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {

    private final ProfessorRepository professorRepository;
    private final SubjectService subjectService;

    public ProfessorService(ProfessorRepository professorRepository, SubjectService subjectService){
        this.professorRepository = professorRepository;
        this.subjectService= subjectService;
    }


    @Transactional
    public void registerProfessor(Professor professor, int subjectEntityId){
        Subject subject = subjectService.ensureSubjectExist(subjectEntityId);
        if(subject.getProfessorId() != null){
            throw new OperationConflictException("Subject with id " + subjectEntityId + " already has a professor");
        }

        professorRepository.save(professor);
        assignProfessorToSubject(professor, subject);
    }

    private void assignProfessorToSubject(Professor professor, Subject subject) {
        int id = subject.getId();
        Subject subjectWithProfessor = Subject.newBuilder()
                .withId(id)
                .withName(subject.getName())
                .withHours(subject.getHours())
                .withProfessorId(professor.getId()).build();

        subjectService.updateSubject(subjectWithProfessor, subjectService.getAllProgramsRelatedToThisSubject(id));
    }


    public void updateProfessor(Professor professor) {
        professorRepository.update(professor);
    }



    public Optional<Professor> findProfessorById(int id){
        return professorRepository.findById(id);
    }

    public List<Professor> findProfessorsByName(String name, int pageSize, int page) {
        return professorRepository.findByName(name, pageSize, page);
    }

    public List<Professor> findAllProfessors(int pageSize, int page) {
        return professorRepository.findAll(pageSize, page);
    }
}