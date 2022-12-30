package ru.martynovevgeniy.vetclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.martynovevgeniy.vetclinic.models.Treatment;
import ru.martynovevgeniy.vetclinic.services.TreatmentService;

import javax.validation.Valid;

@Controller
@RequestMapping("/treatment")
public class TreatmentController {

    private final TreatmentService treatmentService;

    public TreatmentController(TreatmentService treatmentService) {
        this.treatmentService = treatmentService;
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("treatments", treatmentService.list());
        return "treatment/list";
    }

    @GetMapping("/details/{id}")
    public String details(Model model, @PathVariable("id") Long id) {
        Treatment treatment = treatmentService.getById(id);

        model.addAttribute("treatment", treatment);
        model.addAttribute("visits", treatment.getVisits());
        return "treatment/details";
    }

    @GetMapping("/create")
    public String createGet(Model model) {
        model.addAttribute("treatment", new Treatment());
        return "treatment/create";
    }

    @PostMapping("/create")
    public String createPost(@ModelAttribute("treatment") @Valid Treatment treatment, BindingResult bindingResult) {
        checkForErrors(treatment, bindingResult);
        if (bindingResult.hasErrors()) {
            return "treatment/create";
        }

        treatment = treatmentService.create(treatment);
        return "redirect:/treatment/details/" + treatment.getId();
    }

    @GetMapping("/edit/{id}")
    public String editGet(Model model, @PathVariable("id") Long id) {
        model.addAttribute("treatment", treatmentService.getById(id));
        return "treatment/edit";
    }

    @PostMapping("/edit")
    public String editPost(@ModelAttribute("treatment") @Valid Treatment treatment, BindingResult bindingResult) {
        checkForErrors(treatment, bindingResult);
        if (bindingResult.hasErrors()) {
            return "treatment/edit";
        }

        treatmentService.edit(treatment);
        return "redirect:/treatment/details/" + treatment.getId();
    }

    @GetMapping("/delete/{id}")
    public String deleteGet(Model model, @PathVariable("id") Long id) {
        Treatment treatment = treatmentService.getById(id);
        model.addAttribute("treatment", treatment);
        model.addAttribute("hasVisits", treatment.getVisits().size() != 0);
        return "treatment/delete";
    }

    @PostMapping("/delete")
    public String deletePost(Model model, @ModelAttribute("treatment") Treatment treatment) {
        Treatment treatmentDelete = treatmentService.getById(treatment.getId());
        if (treatmentDelete.getVisits().size() != 0) {
            model.addAttribute("treatment", treatmentDelete);
            model.addAttribute("hasVisits", true);
            return "treatment/delete";
        }

        treatmentService.delete(treatmentDelete.getId());
        return "redirect:/treatment/list";
    }

    private void checkForErrors(Treatment treatment, BindingResult bindingResult) {
        if (treatment.getCost() != null && treatment.getCost() < 0) {
            bindingResult.addError(new FieldError(
                    "treatment", "cost",
                    treatment.getCost(),
                    false, null, null,
                    "Стоимость не может быть меньше 0")
            );
        }
        if (checkName(treatment)) {
            bindingResult.addError(new FieldError(
                    "treatment", "name",
                    treatment.getName(),
                    false, null, null,
                    "Данное название уже используется")
            );
        }
    }

    private boolean checkName(Treatment treatment) {
        if (treatment.getId() == null) {
            return treatmentService.checkName(treatment.getName());
        } else {
            return treatmentService.checkNameExceptId(treatment.getId(), treatment.getName());
        }
    }
}