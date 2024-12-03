/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.jsy.eng_software_urna_eletronica;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
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
        					Boolean isCivil = eCargo.equalsIgnoreCase("CIVIL");
        					Boolean validChoice2 = false;
        					
        					do {
	        					System.out.println("Informe o cargo da pessoa: ");        			    	        
				    	        System.out.println("1. SENADOR");
				    	        System.out.println("2. DEPUTADO");
				    	        System.out.println("3. GOVERNADOR");
				    	        System.out.println("4. PRESIDENTE");
				    	        int cargoChoice2 = sc.nextInt();
				    	        
				    	        switch(cargoChoice2) {
				    	        case 1:
				    	        	eCargo = "SENADOR";
				    	        	validChoice2 = true;
				    	        	break;
				    	        case 2:
				    	        	eCargo = "DEPUTADO";
				    	        	validChoice2 = true;
				    	        	break;
		    	        		case 3:
				    	        	eCargo = "GOVERNADOR";
				    	        	validChoice2 = true;
				    	        	break;
								case 4:
									eCargo = "PRESIDENTE";
									validChoice2 = true;
									break;
				    	        }
        					}while(!validChoice2);
        					
        					if(isCivil) {
        						System.out.println("Informe o partido do usuário: ");
        						ePartido = sc.nextLine();
        					}
        					
        					break;
        				default:
        					break;
        				}
        				
        			}while(eChoice != 4);
    			}
    			
        		break;
    		case 4:
    			int vChoice = sc.nextInt();
    	        
    			do {
    				System.out.println("------------ VOTAÇÃO ------------");
    				Voting.startVoting();
    				System.out.println("Informe a opção desejada: ");        			    	        
    				System.out.println("1. Votar");
    				System.out.println("2. Finalizar");    	        
    				
    				System.out.println("Informe para qual cargo deseja votar: ");
        	        System.out.println("1. Senador");
        	        System.out.println("2. Deputado");
        	        System.out.println("3. Governador");
        	        System.out.println("4. Presidente");    	 
    				int vcChoice = sc.nextInt();
    				String vCargo = null;
    				
    				switch(vcChoice) {
    				case 1:
    					vCargo = "SENADOR";
    					break;
    				case 2:
    					vCargo = "DEPUTADO";
    					break;
    				case 3:
    					vCargo = "GOVERNADOR";
    					break;
    				case 4:
    					vCargo = "PRESIDENTE";
    					break;
					default:
						System.out.println("Cargo inválido.");
						break;
    				}
        	        
    				int currentPage = 1;
    				Boolean voted = false;
        	        if(vCargo != null){        	        
        	        	do {
            	        	System.out.println("Listando Candidatos do Cargo [" + vCargo + "]");
            	        	List<User> candidatos = Database.listCandidates(vCargo, currentPage);
            	        	
            	        	for(User pessoa : candidatos) {
            	        		pessoa.toString();
            	        	}
            	        	
            	        	System.out.println("Página atual: " + currentPage);
            	        	System.out.println("Informe o que deseja fazer: ");
            	        	System.out.println("1. Página para direita(+1)");
            	        	System.out.println("2. Página para esquerda(-1)");
            	        	System.out.println("3. Votar");
            	        	int fvChoice = sc.nextInt();
            	        	
            	        	
            	        	switch(fvChoice) {
            	        	case 1:
            	        		currentPage += 1;
            	        		break;
            	        	case 2:
            	        		currentPage = Math.max(1, currentPage - 1);
            	        		break;
            	        	case 3:
            	        		System.out.println("Informe o id do candidato; ");
            	        		Integer candidateId = sc.nextInt();
            	        		
            	        		Voting.makeVote(candidateId);
            	        		voted = true;
            	        		break;
            	        	default:
            	        		System.out.println("Entrada inválida");            	       
            	        		break;
            	        	}
        	        	}while(!voted);
        	        }
        	        
    			}while(vChoice == 1);
    			
        		break;
    		case 5:
    			System.out.println("------------ RELATÓRIO ------------");
    			List<String> votacoes = Database.listVotesTables();
    			System.out.println("Lista de votações no histórico: ");
    			
    			for(int i = 0; i < votacoes.size(); i++) {
    				System.out.println(i + ". " + votacoes.get(i));
    			}
    			System.out.println("Qual votação você deseja ver o relatório: ");
    			
    			
        		break;
    		}
    	}while(c0 != 6 ); // Close Menu
//    	Database.seedDatabase(500);
    	
    	User teste = new User(0,"Jhony","PTsOL","PRESIDENTE");
    	Database.insertUser(teste);
    	Database.removeUser(499);
    	
    }
}
