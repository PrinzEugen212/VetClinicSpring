package ru.martynovevgeniy.vetclinic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.martynovevgeniy.vetclinic.models.Animal;

public interface AnimalRepository extends JpaRepository<Animal, Long> {

}