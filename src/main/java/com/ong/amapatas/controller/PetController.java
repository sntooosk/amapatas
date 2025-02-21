package com.ong.amapatas.controller;

import com.ong.amapatas.entity.Pet;
import com.ong.amapatas.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetRepository petRepository;

    private static final String UPLOAD_DIR = "src/main/resources/static/imagens/";

    @GetMapping
    public String listPets(Model model) {
        model.addAttribute("pets", petRepository.findAll());
        return "pet/listaPet";
    }

    @GetMapping("/adicionar")
    public String showForm() {
        return "pet/criarPet";
    }

    @PostMapping("/adicionar")
    public String addPet(@RequestParam String nome, @RequestParam String sexo, @RequestParam Double peso,
                         @RequestParam String pelagem, @RequestParam Integer anoDeNascimento, @RequestParam String raca,
                         @RequestParam MultipartFile foto, RedirectAttributes redirectAttributes) throws IOException {

        String fileName = foto.getOriginalFilename();
        Path path = Paths.get(UPLOAD_DIR + fileName);
        Files.write(path, foto.getBytes());

        Pet pet = new Pet(nome, sexo, peso, pelagem, anoDeNascimento, raca, "/imagens/" + fileName);
        petRepository.save(pet);

        redirectAttributes.addFlashAttribute("message", "Pet adicionado com sucesso!");
        return "redirect:/pets";
    }

    @GetMapping("/editar/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        petRepository.findById(id).ifPresent(pet -> model.addAttribute("pet", pet));
        return petRepository.existsById(id) ? "pet/editarPet" : "redirect:/pets";
    }

    @PostMapping("/editar/{id}")
    public String editPet(@PathVariable Long id, @RequestParam String nome, @RequestParam String sexo, @RequestParam Double peso,
                          @RequestParam String pelagem, @RequestParam Integer anoDeNascimento, @RequestParam String raca,
                          @RequestParam MultipartFile foto, RedirectAttributes redirectAttributes) throws IOException {

        return petRepository.findById(id).map(pet -> {
            pet.setNome(nome);
            pet.setSexo(sexo);
            pet.setPeso(peso);
            pet.setPelagem(pelagem);
            pet.setAnoDeNascimento(anoDeNascimento);
            pet.setRaca(raca);

            if (!foto.isEmpty()) {
                String fileName = foto.getOriginalFilename();
                Path path = Paths.get(UPLOAD_DIR + fileName);
                try {
                    Files.write(path, foto.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                pet.setFoto("/imagens/" + fileName);
            }

            petRepository.save(pet);
            redirectAttributes.addFlashAttribute("message", "Pet atualizado com sucesso!");
            return "redirect:/pets";
        }).orElse("redirect:/pets");
    }

    @GetMapping("/excluir/{id}")
    public String deletePet(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        petRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Pet excluído com sucesso!");
        return "redirect:/pets";
    }
}

