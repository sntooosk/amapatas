package com.ong.amapatas.controller;

import com.ong.amapatas.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetService petService;

    @GetMapping
    public String listPets(Model model) {
        model.addAttribute("pets", petService.findAll());
        return "pet/listaPet";
    }

    @GetMapping("/adicionar")
    public String showForm() {
        return "pet/criarPet";
    }

    @PostMapping("/adicionar")
    public String addPet(@RequestParam String nome, @RequestParam String sexo, @RequestParam Double peso,
                         @RequestParam String pelagem, @RequestParam Integer anoDeNascimento, @RequestParam String raca,
                         @RequestParam MultipartFile foto, RedirectAttributes redirectAttributes) {
        petService.addPet(nome, sexo, peso, pelagem, anoDeNascimento, raca, foto, redirectAttributes);
        return "redirect:/pets";
    }

    @GetMapping("/editar/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        petService.showEditForm(id, model);
        return petService.existsById(id) ? "pet/editarPet" : "redirect:/pets";
    }

    @PostMapping("/editar/{id}")
    public String editPet(@PathVariable Long id, @RequestParam String nome, @RequestParam String sexo, @RequestParam Double peso,
                          @RequestParam String pelagem, @RequestParam Integer anoDeNascimento, @RequestParam String raca,
                          @RequestParam MultipartFile foto, RedirectAttributes redirectAttributes) {
        petService.editPet(id, nome, sexo, peso, pelagem, anoDeNascimento, raca, foto, redirectAttributes);
        return "redirect:/pets";
    }

    @GetMapping("/excluir/{id}")
    public String deletePet(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        petService.deletePet(id, redirectAttributes);
        return "redirect:/pets";
    }
}
