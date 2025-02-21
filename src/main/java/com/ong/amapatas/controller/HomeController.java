package com.ong.amapatas.controller;

import com.ong.amapatas.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    @Autowired
    private PetRepository petRepository;

    @GetMapping("/")
    public String listPets(Model model) {
        model.addAttribute("pets", petRepository.findAll());
        return "home";
    }

    @GetMapping("/pets-adocao/{id}")
    public String showPetDetails(@PathVariable Long id, Model model) {
        petRepository.findById(id).ifPresent(pet -> model.addAttribute("pet", pet));
        return petRepository.existsById(id) ? "home-info" : "redirect:/pets-adocao";
    }
}
