package com.ong.amapatas.service.impl;

import com.ong.amapatas.entity.Pet;
import com.ong.amapatas.repository.PetRepository;
import com.ong.amapatas.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private PetRepository petRepository;

    private static final String UPLOAD_DIR = "src/main/resources/static/imagens/";

    @Override
    public void addPet(String nome, String sexo, Double peso, String pelagem, Integer anoDeNascimento, String raca, MultipartFile foto, RedirectAttributes redirectAttributes) {
        try {
            String fileName = foto.getOriginalFilename();
            Path path = Paths.get(UPLOAD_DIR + fileName);
            Files.write(path, foto.getBytes());

            Pet pet = new Pet(nome, sexo, peso, pelagem, anoDeNascimento, raca, "/imagens/" + fileName);
            petRepository.save(pet);
            redirectAttributes.addFlashAttribute("message", "Pet adicionado com sucesso!");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao salvar a foto.");
        }
    }

    @Override
    public void editPet(Long id, String nome, String sexo, Double peso, String pelagem, Integer anoDeNascimento, String raca, MultipartFile foto, RedirectAttributes redirectAttributes) {
        petRepository.findById(id).ifPresent(pet -> {
            pet.setNome(nome);
            pet.setSexo(sexo);
            pet.setPeso(peso);
            pet.setPelagem(pelagem);
            pet.setAnoDeNascimento(anoDeNascimento);
            pet.setRaca(raca);

            try {
                if (!foto.isEmpty()) {
                    String fileName = foto.getOriginalFilename();
                    Path path = Paths.get(UPLOAD_DIR + fileName);
                    Files.write(path, foto.getBytes());
                    pet.setFoto("/imagens/" + fileName);
                }

                petRepository.save(pet);
                redirectAttributes.addFlashAttribute("message", "Pet atualizado com sucesso!");
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("error", "Erro ao salvar a foto.");
            }
        });
    }

    @Override
    public void deletePet(Long id, RedirectAttributes redirectAttributes) {
        petRepository.findById(id).ifPresent(pet -> {
            Path path = Paths.get(UPLOAD_DIR + pet.getFoto().replace("/imagens/", ""));
            try {
                Files.deleteIfExists(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            petRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Pet excluído com sucesso!");
        });
    }

    @Override
    public void showEditForm(Long id, Model model) {
        petRepository.findById(id).ifPresent(pet -> model.addAttribute("pet", pet));
    }

    @Override
    public boolean existsById(Long id) {
        return petRepository.existsById(id);
    }

    @Override
    public Iterable<Pet> findAll() {
        return petRepository.findAll();
    }
    @Override
    public void findById(Long id, Model model) {
        petRepository.findById(id).ifPresent(pet -> model.addAttribute("pet", pet));
    }
}
