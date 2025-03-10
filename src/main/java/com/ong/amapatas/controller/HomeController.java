package com.ong.amapatas.controller;

import com.ong.amapatas.repository.PetRepository;
import com.ong.amapatas.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    @Autowired
    private PetService petService;

    @GetMapping("/")
    public String listPets(Model model) {
        model.addAttribute("pets", petService.findAll());
        return "home";
    }

    @GetMapping("/pets-adocao/{id}")
    public String showPetDetails(@PathVariable Long id, Model model) {
        petService.findById(id, model);
        return petService.existsById(id) ? "home-info" : "redirect:/pets-adocao";
    }
}
