package ru.martynovevgeniy.vetclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.martynovevgeniy.vetclinic.InteractionPhoto;
import ru.martynovevgeniy.vetclinic.models.Visit;
import ru.martynovevgeniy.vetclinic.services.VisitService;

@Controller
@RequestMapping("/visit")
public class VisitController {

    private final VisitService visitService;

    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("visits", visitService.list());
        return "visit/list";
    }

    @GetMapping("/details/{id}")
    public String details(Model model, @PathVariable("id") Long id) {
        Visit visit = visitService.getById(id);
        if (visit.getAnimal().getPath() != null) {
            try {
                visit.getAnimal().setPath(InteractionPhoto.getPhoto(visit.getAnimal()));
            } catch (Exception exp) {
                visit.getAnimal().setPath(null);
            }
        }

        model.addAttribute("visit", visit);
        model.addAttribute("hasHelperEmployee", visit.getHelperEmployee() != null);
        return "visit/details";
    }

    @GetMapping("/delete/{id}")
    public String deleteGet(Model model, @PathVariable("id") Long id) {
        model.addAttribute("visit", visitService.getById(id));
        return "visit/delete";
    }

    @PostMapping("/delete")
    public String deletePost(Model model, @ModelAttribute("visit") Visit visit) {
        visitService.delete(visit.getId());
        return "redirect:/visit/list";
    }
}