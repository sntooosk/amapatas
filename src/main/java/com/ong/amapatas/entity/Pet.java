package com.ong.amapatas.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tb01_pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "tb01_id")
    private Long id;

    @Column(name = "tb01_nome")
    private String nome;

    @Column(name = "tb01_sexo")
    private String sexo;

    @Column(name = "tb01_peso")
    private Double peso;

    @Column(name = "tb01_pelagem")
    private String pelagem;

    @Column(name = "tb01_ano_de_nascimento")
    private Integer anoDeNascimento;

    @Column(name = "tb01_raca")
    private String raca;

    @Column(name = "tb01_foto")
    private String foto;

    // Construtor sem parâmetros
    public Pet() {
    }

    public Pet(String nome, String sexo, Double peso, String pelagem, Integer anoDeNascimento, String raca, String foto) {
        this.nome = nome;
        this.sexo = sexo;
        this.peso = peso;
        this.pelagem = pelagem;
        this.anoDeNascimento = anoDeNascimento;
        this.raca = raca;
        this.foto = foto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public String getPelagem() {
        return pelagem;
    }

    public void setPelagem(String pelagem) {
        this.pelagem = pelagem;
    }

    public Integer getAnoDeNascimento() {
        return anoDeNascimento;
    }

    public void setAnoDeNascimento(Integer anoDeNascimento) {
        this.anoDeNascimento = anoDeNascimento;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
