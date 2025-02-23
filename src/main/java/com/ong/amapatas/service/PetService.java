package com.ong.amapatas.service;

import com.ong.amapatas.entity.Pet;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.multipart.MultipartFile;

public interface PetService {

    void addPet(String nome, String sexo, Double peso, String pelagem, Integer anoDeNascimento, String raca, MultipartFile foto, RedirectAttributes redirectAttributes);

    void editPet(Long id, String nome, String sexo, Double peso, String pelagem, Integer anoDeNascimento, String raca, MultipartFile foto, RedirectAttributes redirectAttributes);

    void deletePet(Long id, RedirectAttributes redirectAttributes);

    void showEditForm(Long id, Model model);

    Iterable<Pet> findAll();

    void findById(Long id, Model model);

    boolean existsById(Long id);
}