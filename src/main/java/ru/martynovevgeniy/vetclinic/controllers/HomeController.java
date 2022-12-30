package ru.martynovevgeniy.vetclinic.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping()
    public String home(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = authentication.getAuthorities().toArray()[0].toString();

        switch (role) {
            case "ROLE_USER" -> {
                model.addAttribute("authenticated", true);
                model.addAttribute("user", true);
            }
            case "ROLE_OPERATOR", "ROLE_ADMIN" -> {
                model.addAttribute("authenticated", true);
                model.addAttribute("user", false);
            }
            default -> {
                model.addAttribute("authenticated", false);
                model.addAttribute("user", false);
            }
        }
        return "index";
    }

}