package ru.martynovevgeniy.vetclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.martynovevgeniy.vetclinic.models.Employee;
import ru.martynovevgeniy.vetclinic.services.EmployeeService;

import javax.validation.Valid;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("employees", employeeService.list());
        return "employee/list";
    }

    @GetMapping("/details/{id}")
    public String details(Model model, @PathVariable("id") Long id) {
        Employee employee = employeeService.getById(id);
        model.addAttribute("employee", employee);
        model.addAttribute("visits", employee.getVisits());
        model.addAttribute("visitsHelp", employee.getVisitsHelp());
        return "employee/details";
    }

    @GetMapping("/create")
    public String createGet(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee/create";
    }

    @PostMapping("/create")
    public String createPost(@ModelAttribute("employee") @Valid Employee employee, BindingResult bindingResult) {
        checkForUniqueness(employee, bindingResult);
        if (bindingResult.hasErrors()) {
            return "employee/create";
        }

        employee = employeeService.create(employee);
        return "redirect:/employee/details/" + employee.getId();
    }

    @GetMapping("/edit/{id}")
    public String editGet(Model model, @PathVariable("id") Long id) {
        model.addAttribute("employee", employeeService.getById(id));
        return "employee/edit";
    }

    @PostMapping("/edit")
    public String editPost(@ModelAttribute("employee") @Valid Employee employee, BindingResult bindingResult) {
        checkForUniqueness(employee, bindingResult);
        if (bindingResult.hasErrors()) {
            return "employee/edit";
        }

        employeeService.edit(employee);
        return "redirect:/employee/details/" + employee.getId();
    }

    @GetMapping("/delete/{id}")
    public String deleteGet(Model model, @PathVariable("id") Long id) {
        Employee employee = employeeService.getById(id);
        model.addAttribute("employee", employee);
        model.addAttribute("hasVisits", employee.getVisits().size() != 0 || employee.getVisitsHelp().size() != 0);
        return "employee/delete";
    }

    @PostMapping("/delete")
    public String deletePost(Model model, @ModelAttribute("employee") Employee employee) {
        Employee employeeDelete = employeeService.getById(employee.getId());
        if (employeeDelete.getVisits().size() != 0 || employeeDelete.getVisitsHelp().size() != 0) {
            model.addAttribute("employee", employeeDelete);
            model.addAttribute("hasVisits", true);
            return "employee/delete";
        }

        employeeService.delete(employeeDelete.getId());
        return "redirect:/employee/list";
    }

    private void checkForUniqueness(Employee employee, BindingResult bindingResult) {
        if (checkPhone(employee)) {
            bindingResult.addError(new FieldError(
                    "employee", "phone",
                    employee.getPhone(),
                    false, null, null,
                    "Данный телефон уже используется")
            );
        }
        if (checkEmail(employee)) {
            bindingResult.addError(new FieldError(
                    "employee", "email",
                    employee.getEmail(),
                    false, null, null,
                    "Данный email уже используется")
            );
        }
    }

    private boolean checkPhone(Employee employee) {
        if (employee.getId() == null) {
            return employeeService.checkPhone(employee.getPhone());
        } else {
            return employeeService.checkPhoneExceptId(employee.getId(), employee.getPhone());
        }
    }

    private boolean checkEmail(Employee employee) {
        if (employee.getId() == null) {
            return employeeService.checkEmail(employee.getEmail());
        } else {
            return employeeService.checkEmailExceptId(employee.getId(), employee.getEmail());
        }
    }
}