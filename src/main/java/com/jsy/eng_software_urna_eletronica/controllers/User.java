/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jsy.eng_software_urna_eletronica.controllers;

/**
 *
 * @author Joao
 */
public class User {
    private Integer id;
    private String nome;
    private String partido;
    private String cargo;

    // Construtor principal
    public User(Integer id, String nome, String partido, String cargo) {
        this.id = id;
        this.nome = nome;
        this.partido = partido != null ? partido : ""; // Evita null
        this.cargo = cargo != null ? cargo : "";       // Evita null
    }

    // Construtor auxiliar para usuários sem partido
    public User(Integer id, String nome, String cargo) {
        this(id, nome, null, cargo); // Chama o construtor principal com partido como null
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPartido() {
        return partido;
    }

    public void setPartido(String partido) {
        this.partido = partido;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", partido='" + partido + '\'' +
                ", cargo='" + cargo + '\'' +
                '}';
    }
}
