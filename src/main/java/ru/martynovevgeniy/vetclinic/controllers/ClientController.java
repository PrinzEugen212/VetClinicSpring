package ru.martynovevgeniy.vetclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.martynovevgeniy.vetclinic.InteractionPhoto;
import ru.martynovevgeniy.vetclinic.models.Animal;
import ru.martynovevgeniy.vetclinic.models.Client;
import ru.martynovevgeniy.vetclinic.services.ClientService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("clients", clientService.list());
        return "client/list";
    }

    @GetMapping("/details/{id}")
    public String details(Model model, @PathVariable("id") Long id) {
        Client client = clientService.getById(id);
        List<Animal> animals = client.getAnimals();
        for (var animal : animals) {
            if (animal.getPath() != null) {
                try {
                    animal.setPath(InteractionPhoto.getPhoto(animal));
                } catch (Exception exp) {
                    animal.setPath(null);
                }
            }
        }

        model.addAttribute("client", client);
        model.addAttribute("animals", animals);
        model.addAttribute("visits", client.getVisits());
        return "client/details";
    }

    @GetMapping("/create")
    public String createGet(Model model) {
        model.addAttribute("client", new Client());
        return "client/create";
    }

    @PostMapping("/create")
    public String createPost(@ModelAttribute("client") @Valid Client client, BindingResult bindingResult) {
        if (client.getEmail().isEmpty()) client.setEmail(null);

        checkForUniqueness(client, bindingResult);
        if (bindingResult.hasErrors()) {
            return "client/create";
        }

        client = clientService.create(client);
        return "redirect:/client/details/" + client.getId();
    }

    @GetMapping("/edit/{id}")
    public String editGet(Model model, @PathVariable("id") Long id) {
        model.addAttribute("client", clientService.getById(id));
        return "client/edit";
    }

    @PostMapping("/edit")
    public String editPost(@ModelAttribute("client") @Valid Client client, BindingResult bindingResult) {
        if (client.getEmail().isEmpty()) client.setEmail(null);

        checkForUniqueness(client, bindingResult);
        if (bindingResult.hasErrors()) {
            return "client/edit";
        }

        clientService.edit(client);
        return "redirect:/client/details/" + client.getId();
    }

    @GetMapping("/delete/{id}")
    public String deleteGet(Model model, @PathVariable("id") Long id) {
        Client client = clientService.getById(id);
        model.addAttribute("client", client);
        model.addAttribute("hasAnimals", client.getAnimals().size() != 0);
        return "client/delete";
    }

    @PostMapping("/delete")
    public String deletePost(Model model, @ModelAttribute("client") Client client) {
        Client clientDelete = clientService.getById(client.getId());
        if (clientDelete.getAnimals().size() != 0) {
            model.addAttribute("client", clientDelete);
            model.addAttribute("hasAnimals", true);
            return "client/delete";
        }

        clientService.delete(clientDelete.getId());
        return "redirect:/client/list";
    }

    private void checkForUniqueness(Client client, BindingResult bindingResult) {
        if (checkPhone(client)) {
            bindingResult.addError(new FieldError(
                    "client", "phone",
                    client.getPhone(),
                    false, null, null,
                    "Данный телефон уже используется")
            );
        }
        if (checkEmail(client)) {
            bindingResult.addError(new FieldError(
                    "client", "email",
                    client.getEmail(),
                    false, null, null,
                    "Данный email уже используется")
            );
        }
    }

    private boolean checkPhone(Client client) {
        if (client.getId() == null) {
            return clientService.checkPhone(client.getPhone());
        } else {
            return clientService.checkPhoneExceptId(client.getId(), client.getPhone());
        }
    }

    private boolean checkEmail(Client client) {
        if (client.getEmail() != null) {
            if (client.getId() == null) {
                return clientService.checkEmail(client.getEmail());
            } else {
                return clientService.checkEmailExceptId(client.getId(), client.getEmail());
            }
        } else {
            return false;
        }
    }
}