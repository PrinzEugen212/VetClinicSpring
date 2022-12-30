package ru.martynovevgeniy.vetclinic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.martynovevgeniy.vetclinic.models.Visit;

public interface VisitRepository extends JpaRepository<Visit, Long> {

}