package ru.martynovevgeniy.vetclinic.services;

import org.springframework.stereotype.Service;
import ru.martynovevgeniy.vetclinic.models.Animal;
import ru.martynovevgeniy.vetclinic.repositories.AnimalRepository;

import java.util.List;

@Service
public class AnimalService {

    private final AnimalRepository animalRepository;

    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    public List<Animal> list() {
        return animalRepository.findAll();
    }

    public Animal getById(Long id) {
        return animalRepository.findById(id).get();
    }

    public Animal create(Animal animal) {
        return animalRepository.save(animal);
    }

    public void edit(Animal animal) {
        animalRepository.save(animal);
    }

    public void delete(Long id) {
        animalRepository.deleteById(id);
    }
}