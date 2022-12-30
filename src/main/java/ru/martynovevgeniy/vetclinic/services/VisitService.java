package ru.martynovevgeniy.vetclinic.services;

import org.springframework.stereotype.Service;
import ru.martynovevgeniy.vetclinic.models.Visit;
import ru.martynovevgeniy.vetclinic.repositories.VisitRepository;

import java.util.List;

@Service
public class VisitService {

    private final VisitRepository visitRepository;

    public VisitService(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    public List<Visit> list() {
        return visitRepository.findAll();
    }

    public Visit getById(Long id) {
        return visitRepository.findById(id).get();
    }

    public Visit create(Visit visit) {
        return visitRepository.save(visit);
    }

    public void edit(Visit visit) {
        visitRepository.save(visit);
    }

    public void delete(Long id) {
        visitRepository.deleteById(id);
    }
}