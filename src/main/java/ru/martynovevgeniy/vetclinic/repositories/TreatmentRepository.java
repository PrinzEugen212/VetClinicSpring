package ru.martynovevgeniy.vetclinic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.martynovevgeniy.vetclinic.models.Treatment;

public interface TreatmentRepository extends JpaRepository<Treatment, Long> {

    boolean existsByName(String name);

    @Query(
            value = "SELECT CASE WHEN EXISTS (SELECT * FROM treatment WHERE name = :name AND id <> :id) THEN 'true' ELSE 'false' END",
            nativeQuery = true)
    boolean existsByNameExceptId(Long id, String name);

}