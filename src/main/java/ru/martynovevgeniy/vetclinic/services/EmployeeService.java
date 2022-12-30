package ru.martynovevgeniy.vetclinic.services;

import org.springframework.stereotype.Service;
import ru.martynovevgeniy.vetclinic.models.Employee;
import ru.martynovevgeniy.vetclinic.repositories.EmployeeRepository;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> list() {
        return employeeRepository.findAll();
    }

    public Employee getById(Long id) {
        return employeeRepository.findById(id).get();
    }

    public Employee getByEmail(String email) {
        return employeeRepository.findByEmail(email).get();
    }

    public Employee create(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void edit(Employee employee) {
        employeeRepository.save(employee);
    }

    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }

    public boolean checkPhone(String phone) {
        return employeeRepository.existsByPhone(phone);
    }

    public boolean checkEmail(String email) {
        return employeeRepository.existsByEmail(email);
    }

    public boolean checkPhoneExceptId(Long id, String phone) {
        return employeeRepository.existsByPhoneExceptId(id, phone);
    }

    public boolean checkEmailExceptId(Long id, String email) {
        return employeeRepository.existsByEmailExceptId(id, email);
    }
}