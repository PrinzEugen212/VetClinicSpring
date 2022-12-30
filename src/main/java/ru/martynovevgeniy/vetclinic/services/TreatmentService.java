package ru.martynovevgeniy.vetclinic.services;

import org.springframework.stereotype.Service;
import ru.martynovevgeniy.vetclinic.models.Treatment;
import ru.martynovevgeniy.vetclinic.repositories.TreatmentRepository;

import java.util.List;

@Service
public class TreatmentService {

    private final TreatmentRepository treatmentRepository;

    public TreatmentService(TreatmentRepository treatmentRepository) {
        this.treatmentRepository = treatmentRepository;
    }

    public List<Treatment> list() {
        return treatmentRepository.findAll();
    }

    public Treatment getById(Long id) {
        return treatmentRepository.findById(id).get();
    }

    public Treatment create(Treatment treatment) {
        return treatmentRepository.save(treatment);
    }

    public void edit(Treatment treatment) {
        treatmentRepository.save(treatment);
    }

    public void delete(Long id) {
        treatmentRepository.deleteById(id);
    }

    public boolean checkName(String name) {
        return treatmentRepository.existsByName(name);
    }

    public boolean checkNameExceptId(Long id, String name) {
        return treatmentRepository.existsByNameExceptId(id, name);
    }
}