package ru.martynovevgeniy.vetclinic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.martynovevgeniy.vetclinic.models.Client;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByEmail(String email);

    boolean existsByPhone(String phone);

    boolean existsByEmail(String email);

    @Query(
            value = "SELECT CASE WHEN EXISTS (SELECT * FROM client WHERE phone = :phone AND id <> :id) THEN 'true' ELSE 'false' END",
            nativeQuery = true)
    boolean existsByPhoneExceptId(Long id, String phone);

    @Query(
            value = "SELECT CASE WHEN EXISTS (SELECT * FROM client WHERE email = :email AND id <> :id) THEN 'true' ELSE 'false' END",
            nativeQuery = true)
    boolean existsByEmailExceptId(Long id, String email);

}