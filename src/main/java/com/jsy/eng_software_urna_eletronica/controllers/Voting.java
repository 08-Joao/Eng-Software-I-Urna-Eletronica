package com.jsy.eng_software_urna_eletronica.controllers;

import java.sql.SQLException;
import java.util.List;

public class Voting {
	public static void startVoting() throws SQLException {
		
		
		List<String> tableNames = Database.listVotesTables();
		Database.initializeVoting(tableNames.size());
		
		System.out.println("Listas encontradas: " + tableNames);
		}
	

}
