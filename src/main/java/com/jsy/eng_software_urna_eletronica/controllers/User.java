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
    private Integer quantity = 0;

    // Construtor principal
    public User(Integer id, String nome, String partido, String cargo) {
        this.id = id;
        this.nome = nome;
        this.partido = partido != null ? partido : ""; 
        this.cargo = cargo != null ? cargo : "";      
    }


    public User(Integer id, String nome, String partido, String cargo, Integer quantity) {
        this.id = id;
        this.nome = nome;
        this.partido = partido != null ? partido : ""; 
        this.cargo = cargo != null ? cargo : "";      
        this.quantity = 0;
    }
    
    public User(Integer id, String nome, String cargo) {
        this(id, nome, null, cargo); 
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    @Override
    public String toString() {
    	if(quantity == 0) {
            return "Usuário [" + id + "] Nome: " + nome + " Partido: " + partido + " Cargo: " + cargo;
    	}else {
    		return "Usuário [" + id + "] Nome: " + nome + " Partido: " + partido + " Cargo: " + cargo + " Votos [" + quantity + "]";
    	}

    }
}
