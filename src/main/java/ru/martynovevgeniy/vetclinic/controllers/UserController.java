package ru.martynovevgeniy.vetclinic.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.martynovevgeniy.vetclinic.InteractionPhoto;
import ru.martynovevgeniy.vetclinic.models.Animal;
import ru.martynovevgeniy.vetclinic.models.Client;
import ru.martynovevgeniy.vetclinic.services.ClientService;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final ClientService clientService;

    public UserController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/visit/list")
    public String listVisits(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Client client = clientService.getByEmail(email);

        model.addAttribute("visits", client.getVisits());
        return "user/visits";
    }

    @GetMapping("/animal/list")
    public String listAnimals(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Client client = clientService.getByEmail(email);

        List<Animal> animals = client.getAnimals();
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
        return "user/animals";
    }

}