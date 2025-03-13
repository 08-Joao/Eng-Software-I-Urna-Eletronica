package com.jsy.eng_software_urna_eletronica.controllers;

public class Candidato extends User{
    
    private String partido;
    private String cargo;
    private Integer quantity = 0;

    public Candidato() {
    }

    public Candidato(Integer id, String nome, String partido, String cargo) {
        super(id,nome);
        this.id = id;
        this.nome = nome;
        this.partido = partido != null ? partido : ""; 
        this.cargo = cargo != null ? cargo : "";      
    }

    public Candidato(Integer id, String nome, String partido, String cargo, Integer quantity) {
        this.id = id;
        this.nome = nome;
        this.partido = partido != null ? partido : ""; 
        this.cargo = cargo != null ? cargo : "";      
        this.quantity = 0;
    }

    public Candidato(Integer id, String nome, String cargo) {
        this(id, nome, null, cargo); 
    }

    public Candidato printCandidato(int id){
        Candidato candidato = Database.getCandidato(id);
        if(candidato != null){
            candidato.getId();
            candidato.getNome();
            candidato.getPartido();
            candidato.getCargo();
        }
        return candidato;
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
