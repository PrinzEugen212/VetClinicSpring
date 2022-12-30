package ru.martynovevgeniy.vetclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.martynovevgeniy.vetclinic.InteractionPhoto;
import ru.martynovevgeniy.vetclinic.models.Animal;
import ru.martynovevgeniy.vetclinic.models.Client;
import ru.martynovevgeniy.vetclinic.models.Employee;
import ru.martynovevgeniy.vetclinic.models.Visit;
import ru.martynovevgeniy.vetclinic.services.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/visit/create")
public class CreateVisitController {

    private final VisitService visitService;
    private final EmployeeService employeeService;
    private final ClientService clientService;
    private final AnimalService animalService;
    private final TreatmentService treatmentService;

    public CreateVisitController(VisitService visitService, EmployeeService employeeService, ClientService clientService, AnimalService animalService, TreatmentService treatmentService) {
        this.visitService = visitService;
        this.employeeService = employeeService;
        this.clientService = clientService;
        this.animalService = animalService;
        this.treatmentService = treatmentService;
    }

    @GetMapping()
    public String createGet(Model model) {
        List<Employee> employees = employeeService.list();
        employees.removeIf(e -> !e.isCanHelp());
        model.addAttribute("employees", employees);
        return "visit/create/chooseHelperEmployee";
    }

    @GetMapping("/chooseHelperEmployee/{helperEmployeeId}")
    public String createHelperEmployee(Model model, @PathVariable("helperEmployeeId") Long helperEmployeeId) {
        model.addAttribute("helperEmployeeId", helperEmployeeId);
        model.addAttribute("clients", clientService.list());
        return "visit/create/chooseClient";
    }

    @GetMapping("/chooseClient/{helperEmployeeId}/{clientId}")
    public String createChooseClient(Model model, @PathVariable("helperEmployeeId") Long helperEmployeeId, @PathVariable("clientId") Long clientId) {
        model.addAttribute("helperEmployeeId", helperEmployeeId);
        model.addAttribute("clientId", clientId);
        List<Animal> animals = clientService.getById(clientId).getAnimals();
        for (var animal : animals) {
            if(animal.getPath() != null) {
                try {
                    animal.setPath(InteractionPhoto.getPhoto(animal));
                } catch (Exception exp) {
                    animal.setPath(null);
                }
            }
        }
        model.addAttribute("animals", animals);
        return "visit/create/chooseAnimal";
    }

    @GetMapping("/chooseAnimal/{helperEmployeeId}/{clientId}/{animalId}")
    public String createChooseAnimal(Model model,
                                     @PathVariable("helperEmployeeId") Long helperEmployeeId,
                                     @PathVariable("clientId") Long clientId,
                                     @PathVariable("animalId") Long animalId) {

        Employee helperEmployee;
        Client client;
        Animal animal;

        if(helperEmployeeId == -1){
            helperEmployee = null;
        }
        else {
            try {
                helperEmployee = employeeService.getById(helperEmployeeId);
                if (!helperEmployee.isCanHelp()) {
                    throw new Exception();
                }
            } catch (Exception exp) {
                return "redirect:/visit/create";
            }
        }

        try {
            client = clientService.getById(clientId);
        } catch (Exception exp) {
            return "redirect:/visit/create/chooseHelperEmployee/" + helperEmployeeId;
        }

        try {
            animal = animalService.getById(animalId);
            if (!client.getAnimals().contains(animal)) {
                throw new Exception();
            }
        } catch (Exception exp) {
            return "redirect:/visit/create/chooseClient/" + helperEmployeeId + "/" + clientId;
        }

        var visit = new Visit();
        visit.setTotalCost(0);
        visit.setHelperEmployee(helperEmployee);
        visit.setClient(client);
        visit.setAnimal(animal);

        visit.setEmployee(employeeService.getById(1L));

        model.addAttribute("visit", visit);
        return "visit/create/chooseInfo";
    }

    @PostMapping("/chooseInfo")
    public String createChooseInfo(Model model, @ModelAttribute("visit") @Valid Visit visit, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "visit/create/chooseInfo";
        }

        model.addAttribute("visit", visit);
        model.addAttribute("listTreatments", treatmentService.list());
        return "visit/create/chooseTreatments";
    }

    @PostMapping("/chooseTreatments")
    public String chooseTreatments(Model model, @ModelAttribute("visit") @Valid Visit visit) {
        for (var treatment: visit.getTreatments()) {
            visit.setTotalCost(visit.getTotalCost() + treatment.getCost());
        }

        if (visit.getAnimal().getPath() != null) {
            try {
                model.addAttribute("photo", InteractionPhoto.getPhoto(visit.getAnimal()));
            } catch (Exception exp) {
                model.addAttribute("photo", null);
            }
        }

        model.addAttribute("visit", visit);
        model.addAttribute("hasHelperEmployee", visit.getHelperEmployee() != null);
        return "visit/create/create";
    }

    @PostMapping()
    public String createPost(@ModelAttribute("visit") @Valid Visit visit) {
        visit = visitService.create(visit);
        return "redirect:/visit/details/" + visit.getId();
    }
}