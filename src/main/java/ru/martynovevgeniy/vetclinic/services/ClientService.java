package ru.martynovevgeniy.vetclinic.services;

import org.springframework.stereotype.Service;
import ru.martynovevgeniy.vetclinic.models.Client;
import ru.martynovevgeniy.vetclinic.repositories.ClientRepository;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> list() {
        return clientRepository.findAll();
    }

    public Client getById(Long id) {
        return clientRepository.findById(id).get();
    }

    public Client getByEmail(String email) {
        return clientRepository.findByEmail(email).get();
    }

    public Client create(Client client) {
        return clientRepository.save(client);
    }

    public void edit(Client client) {
        clientRepository.save(client);
    }

    public void delete(Long id) {
        clientRepository.deleteById(id);
    }

    public boolean checkPhone(String phone) {
        return clientRepository.existsByPhone(phone);
    }

    public boolean checkEmail(String email) {
        return clientRepository.existsByEmail(email);
    }

    public boolean checkPhoneExceptId(Long id, String phone) {
        return clientRepository.existsByPhoneExceptId(id, phone);
    }

    public boolean checkEmailExceptId(Long id, String email) {
        return clientRepository.existsByEmailExceptId(id, email);
    }
}