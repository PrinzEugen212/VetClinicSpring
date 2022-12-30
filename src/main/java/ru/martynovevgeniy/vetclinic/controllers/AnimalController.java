package ru.martynovevgeniy.vetclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.martynovevgeniy.vetclinic.InteractionPhoto;
import ru.martynovevgeniy.vetclinic.models.Animal;
import ru.martynovevgeniy.vetclinic.models.Client;
import ru.martynovevgeniy.vetclinic.services.AnimalService;
import ru.martynovevgeniy.vetclinic.services.ClientService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/animal")
public class AnimalController {

    private final AnimalService animalService;
    private final ClientService clientService;

    public AnimalController(AnimalService animalService, ClientService clientService) {
        this.animalService = animalService;
        this.clientService = clientService;
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<Animal> animals = animalService.list();
        for (var animal : animals) {
            if(animal.getPath() != null) {
                try {
                    animal.setPath(InteractionPhoto.getPhoto(animal));
                } catch (Exception exp) {
                    animal.setPath(null);
                }
            }
        }
        model.addAttribute("animals", animalService.list());
        return "animal/list";
    }

    @GetMapping("/details/{id}")
    public String details(Model model, @PathVariable("id") Long id) {
        Animal animal = animalService.getById(id);
        if(animal.getPath() != null) {
            try {
                animal.setPath(InteractionPhoto.getPhoto(animal));
            } catch (Exception exp) {
                animal.setPath(null);
            }
        }
        model.addAttribute("animal", animal);
        model.addAttribute("visits", animal.getVisits());
        return "animal/details";
    }

    @GetMapping("/create")
    public String createGet(Model model) {
        model.addAttribute("clients", clientService.list());
        return "animal/create/chooseClient";
    }

    @GetMapping("/create/chooseClient/{clientId}")
    public String createChoosePolicyholder(Model model, @PathVariable("clientId") Long clientId) {
        Client client;
        try {
            client = clientService.getById(clientId);
        } catch (Exception exp) {
            model.addAttribute("clients", clientService.list());
            return "policy/create/choosePolicyholder";
        }

        Animal animal = new Animal();
        animal.setClient(client);
        model.addAttribute("animal", animal);
        return "animal/create/chooseInfo";
    }

    @PostMapping("/create")
    public String createPost(@ModelAttribute("animal") @Valid Animal animal, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "animal/create/chooseInfo";
        }

        animal = animalService.create(animal);
        InteractionPhoto.createDir(animal.getId());

        return "redirect:/animal/details/" + animal.getId();
    }

    @GetMapping("/edit/{id}")
    public String editGet(Model model, @PathVariable("id") Long id) {
        model.addAttribute("animal", animalService.getById(id));
        return "animal/edit";
    }

    @PostMapping("/edit")
    public String editPost(@ModelAttribute("animal") @Valid Animal animal, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "animal/edit";
        }

        animalService.edit(animal);
        return "redirect:/animal/details/" + animal.getId();
    }

    @GetMapping("/editPhoto/{animalId}")
    public String editPhotoGet(Model model, @PathVariable("animalId") Long animalId) {
        model.addAttribute("animalId", animalId);
        return "animal/editPhoto";
    }

    @PostMapping("/editPhoto/{animalId}")
    public String editPhotoPost(@PathVariable("animalId") Long animalId, @RequestParam("image") MultipartFile file) {
        Animal animal = animalService.getById(animalId);
        String fileName;
        try {
            fileName = InteractionPhoto.uploadImage(file, animalId);
            InteractionPhoto.deletePhoto(animal);
        } catch (Exception exp) {
            return "animal/editPhoto";
        }

        animal.setPath(fileName);

        animalService.edit(animal);
        return "redirect:/animal/details/" + animal.getId();
    }

    @GetMapping("/delete/{id}")
    public String deleteGet(Model model, @PathVariable("id") Long id) {
        Animal animal = animalService.getById(id);
        model.addAttribute("animal", animal);
        model.addAttribute("hasVisits", animal.getVisits().size() != 0);
        return "animal/delete";
    }

    @PostMapping("/delete")
    public String deletePost(Model model, @ModelAttribute("animal") Animal animal) {
        Animal animalDelete = animalService.getById(animal.getId());
        if (animalDelete.getVisits().size() != 0) {
            model.addAttribute("animal", animalDelete);
            model.addAttribute("hasVisits", true);
            return "animal/delete";
        }

        InteractionPhoto.deleteDir(animal.getId());
        animalService.delete(animalDelete.getId());
        return "redirect:/animal/list";
    }
}