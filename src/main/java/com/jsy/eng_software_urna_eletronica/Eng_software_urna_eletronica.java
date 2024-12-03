/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.jsy.eng_software_urna_eletronica;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import com.jsy.eng_software_urna_eletronica.controllers.Database;
import com.jsy.eng_software_urna_eletronica.controllers.User;
import com.jsy.eng_software_urna_eletronica.controllers.Voting;

/**
 *
 * @author Joao
 */
public class Eng_software_urna_eletronica {

    public static void main(String[] args) throws SQLException {
    	Database db = new Database();
    	Scanner sc = new Scanner(System.in);
    	
    	int c0 = -1;
    	
    	do {
    		System.out.println("-------------------- MENU --------------------");
    		System.out.println("1. Cadastrar Pessoa");
    		System.out.println("2. Remover Pessoa");
    		System.out.println("3. Editar Pessoa");
    		System.out.println("4. Iniciar Votação");
    		System.out.println("5. Relatório de uma Votação");
    		System.out.println("6. Sair");
    		System.out.println("----------------------------------------------");
    		c0 = sc.nextInt();
    		
    		
    		switch(c0) {
    		case 1:
    			System.out.println("------------ CADASTRO ------------");
    	        System.out.println("Informe o nome do usuário: ");
    	        String nome = sc.nextLine();
    	        
    	        System.out.println("Informe o cargo da pessoa: ");
    	        System.out.println("1. CIVIL");
    	        System.out.println("2. SENADOR");
    	        System.out.println("3. DEPUTADO");
    	        System.out.println("4. GOVERNADOR");
    	        System.out.println("5. PRESIDENTE");
    	        int cCargo = sc.nextInt();
    	        sc.nextLine(); // Consome a quebra de linha
    	        
    	        int[] votaveis = new int[]{2, 3, 4, 5};
    	        boolean isVotavel = false;
    	        String Cargo = "CIVIL";
    	        for (int cargo : votaveis) {
    	            if (cargo == cCargo) {
    	                isVotavel = true;
    	                
    	                switch(cCargo) {
    	                case 2:
    	                	Cargo = "SENADOR";
    	                	break;
    	                case 3:
    	                	Cargo = "DEPUTADO";
    	                	break;
    	                case 4:
    	                	Cargo = "GOVERNADOR";
    	                	break;
    	                case 5:
    	                	Cargo = "PRESIDENTE";
    	                	break;
    	                }
    	                break;
    	            }
    	        }

    	        User user = null;
    	        if (isVotavel) {
    	            System.out.println("Informe o partido do usuário");
    	            String partido = sc.nextLine();
    	            user = new User(0, nome, partido,Cargo);
    	        } else {
    	            user = new User(0, nome, null, "CIVIL");
    	        }
    	        Database.insertUser(user);
    			
    			break;
    		case 2:
    			System.out.println("------------ REMOÇÃO ------------");
    			System.out.println("Informe o ID do usuário a ser removido: ");
    			Integer id = sc.nextInt();
    			Database.removeUser(id);
        		break;
    		case 3:
    			System.out.println("------------ EDIÇÃO ------------");
    			System.out.println("Informe o id do usuário a ser editado: ");
    			Integer eId = sc.nextInt();
    			
    			User foundUser = Database.getUser(eId);
    			if(foundUser != null) {
    				int eChoice = -1;
    				
    				String eNome = foundUser.getNome();
    				String ePartido = foundUser.getPartido();
    				String eCargo = foundUser.getCargo();
        			do {
        				System.out.println("Informe o que deseja editar: ");
        				System.out.println("1. Nome");
        				System.out.println("2. Partido");
        				System.out.println("3. Cargo");
        				System.out.println("4. Finalizar");
        				eChoice = sc.nextInt();
        				switch(eChoice) {
        				case 1: 
        					System.out.println("Informe o novo nome do usuário: ");
        					eNome = sc.nextLine();
        					break;
        				case 2:
        					if(ePartido == null) {
        						System.out.println("Informe o partido do usuário: ");
        						ePartido = sc.nextLine();
        						
        						Boolean validChoice = false;
        						do {
        			    	        System.out.println("Informe o cargo da pessoa: ");        			    	        
        			    	        System.out.println("1. SENADOR");
        			    	        System.out.println("2. DEPUTADO");
        			    	        System.out.println("3. GOVERNADOR");
        			    	        System.out.println("4. PRESIDENTE");
        			    	        int cargoChoice = sc.nextInt();
        			    	        
        			    	        switch(cargoChoice) {
        			    	        case 1:
        			    	        	eCargo = "SENADOR";
        			    	        	validChoice = true;
        			    	        	break;
        			    	        case 2:
        			    	        	eCargo = "DEPUTADO";
        			    	        	validChoice = true;
        			    	        	break;
			    	        		case 3:
        			    	        	eCargo = "GOVERNADOR";
        			    	        	validChoice = true;
        			    	        	break;
									case 4:
										eCargo = "PRESIDENTE";
										validChoice = true;
										break;
        			    	        }
        						}while(!validChoice);
        						
        						System.out.println("Informe o partido do usuário: ");
        						ePartido = sc.nextLine();
        					}
        					break;
        				case 3:
        					
        					break;
        				default:
        					break;
        				}
        				
        			}while(eChoice != 4);
    			}
    			
        		break;
    		case 4:
    			
        		break;
    		case 5:
    			
        		break;
    		}
    	}while(c0 != 6 ); // Close Menu
//    	Database.seedDatabase(500);
    	
    	User teste = new User(0,"Jhony","PTsOL","PRESIDENTE");
    	Database.insertUser(teste);
    	Database.removeUser(499);
    	
    }
}
