package com.jsy.eng_software_urna_eletronica.controllers;

public class Votacao {
	Integer id_candidate;
	Integer quantity;
	
	public Votacao(Integer id_candidate, Integer quantity) {
		this.id_candidate = id_candidate;
		this.quantity = quantity;
	}
}
