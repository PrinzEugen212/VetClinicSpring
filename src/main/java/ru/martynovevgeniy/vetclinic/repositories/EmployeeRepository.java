package ru.martynovevgeniy.vetclinic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.martynovevgeniy.vetclinic.models.Employee;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    boolean existsByPhone(String phone);

    boolean existsByEmail(String email);

    @Query(
            value = "SELECT CASE WHEN EXISTS (SELECT * FROM employee WHERE phone = :phone AND id <> :id) THEN 'true' ELSE 'false' END",
            nativeQuery = true)
    boolean existsByPhoneExceptId(Long id, String phone);

    @Query(
            value = "SELECT CASE WHEN EXISTS (SELECT * FROM employee WHERE email = :email AND id <> :id) THEN 'true' ELSE 'false' END",
            nativeQuery = true)
    boolean existsByEmailExceptId(Long id, String email);

}