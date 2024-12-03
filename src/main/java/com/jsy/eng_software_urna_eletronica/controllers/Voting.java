package com.jsy.eng_software_urna_eletronica.controllers;

import java.sql.SQLException;
import java.util.List;

public class Voting {
	public static void startVoting() throws SQLException {
		
		List<String> tableNames = Database.listVotesTables();
		Database.initializeVoting(tableNames.size());		
		
		}
	
	public static void makeVote(Integer id) throws SQLException {
		if(Database.getUser(id) != null) {
			Database.makeVote(id);								
		}
	}
	
	
	
	public static void makeRelatorio(String votingTable) throws SQLException {
		System.out.println("Lista dos candidatos da " + votingTable);
		List<User> entradas = Database.listVotacao(votingTable);
		
		for(User candidato : entradas) {
			candidato.toString();
		}
		
		System.out.println("-------- ELEGIDOS --------");
		List<User> elegidos = Database.getElected(votingTable);
		
		for(User elegido : elegidos) {
			if(elegido.getCargo().compareToIgnoreCase("CIVIL") == 0) {				
				System.out.println(elegido.toString());
			}
		}
	}

}
