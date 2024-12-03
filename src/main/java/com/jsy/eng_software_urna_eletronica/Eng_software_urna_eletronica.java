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
    		System.out.println("6. Popular Base de Dados");
    		System.out.println("7. Sair");
    		System.out.println("----------------------------------------------");
    		c0 = sc.nextInt();
    		
    		
    		switch(c0) {
    		case 1:
    			System.out.println("------------ CADASTRO ------------");
    			sc.nextLine();
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
        					sc.nextLine();
        					System.out.println("Informe o novo nome do usuário: ");
        					eNome = sc.nextLine();
        					break;
        				case 2:
        					if(ePartido == null || ePartido.trim().isEmpty()) {
        						ePartido = null;
        						System.out.println("Informe o partido do usuário: ");
        						sc.nextLine();
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
        						

        					}else {
        						System.out.println("Informe o partido do usuário: ");
        						sc.nextLine();
        						ePartido = sc.nextLine();
        					}
        					break;
        				case 3:
        					Boolean isCivil = eCargo.equalsIgnoreCase("CIVIL");
        					Boolean validChoice2 = false;
        					
        					do {
	        					System.out.println("Informe o cargo da pessoa: ");    
	        					System.out.println("1. CIVIL");
				    	        System.out.println("2. SENADOR");
				    	        System.out.println("3. DEPUTADO");
				    	        System.out.println("4. GOVERNADOR");
				    	        System.out.println("5. PRESIDENTE");
				    	        int cargoChoice2 = sc.nextInt();
				    	        
				    	        switch(cargoChoice2) {
				    	        case 1:
				    	        	eCargo = "CIVIL";
				    	        	ePartido = null;
				    	        	validChoice2 = true;
				    	        	break;
				    	        case 2:
				    	        	eCargo = "SENADOR";
				    	        	validChoice2 = true;
				    	        	break;
				    	        case 3:
				    	        	eCargo = "DEPUTADO";
				    	        	validChoice2 = true;
				    	        	break;
		    	        		case 4:
				    	        	eCargo = "GOVERNADOR";
				    	        	validChoice2 = true;
				    	        	break;
								case 5:
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
        			
        			User editedUser = new User(eId,eNome,ePartido,eCargo);
        			Database.editUser(editedUser);
    			}
    			
        		break;
    		case 4:
    		    int vChoice;
    		    Voting.startVoting(); 
    		    do {
    		        System.out.println("------------ VOTAÇÃO ------------");
    		        System.out.println("Informe a opção desejada:");
    		        System.out.println("1. Votar");
    		        System.out.println("2. Finalizar");
    		        
    		        while (!sc.hasNextInt()) { // Prevenir erro com entradas inválidas
    		            System.out.println("Entrada inválida. Informe 1 para votar ou 2 para finalizar.");
    		            sc.next(); // Descarta entrada inválida
    		        }
    		        vChoice = sc.nextInt();

    		        if (vChoice == 1) {
    		            System.out.println("Informe para qual cargo deseja votar:");
    		            System.out.println("1. Senador");
    		            System.out.println("2. Deputado");
    		            System.out.println("3. Governador");
    		            System.out.println("4. Presidente");

    		            int vcChoice = sc.nextInt();
    		            String vCargo = null;

    		            switch (vcChoice) {
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
    		                    System.out.println("Cargo inválido. Retornando ao menu principal.");
    		                    continue; 
    		            }

    		            int currentPage = 1;
    		            boolean voted = false;

    		            if (vCargo != null) {
    		                do {
    		                    System.out.println("Listando candidatos do cargo [" + vCargo + "]");
    		                    List<User> candidatos = Database.listCandidates(vCargo, (currentPage - 1) * 100); 

    		                    if (candidatos.isEmpty()) {
    		                        System.out.println("Nenhum candidato encontrado para o cargo selecionado.");
    		                        break; 
    		                    }

    		                    for (User pessoa : candidatos) {
    		                        System.out.println(pessoa.toString());
    		                    }

    		                    System.out.println("Página atual: " + currentPage);
    		                    System.out.println("Informe o que deseja fazer:");
    		                    System.out.println("1. Próxima página (+1)");
    		                    System.out.println("2. Página anterior (-1)");
    		                    System.out.println("3. Votar");
    		                    System.out.println("4. Sair");

    		                    int fvChoice;
    		                    while (!sc.hasNextInt()) {
    		                        System.out.println("Entrada inválida. Informe 1, 2, 3 ou 4.");
    		                        sc.next(); 
    		                    }
    		                    fvChoice = sc.nextInt();

    		                    switch (fvChoice) {
    		                        case 1:
    		                            currentPage++;
    		                            break;
    		                        case 2:
    		                            currentPage = Math.max(1, currentPage - 1); 
    		                            break;
    		                        case 3:
    		                            System.out.println("Informe o ID do candidato:");
    		                            while (!sc.hasNextInt()) {
    		                                System.out.println("Entrada inválida. Informe um número para o ID.");
    		                                sc.next(); 
    		                            }
    		                            int candidateId = sc.nextInt();

    		                            try {
    		                                Voting.makeVote(candidateId);
    		                                System.out.println("Voto registrado com sucesso!");
    		                                voted = true;
    		                            } catch (Exception e) {
    		                                System.out.println("Erro ao registrar o voto: " + e.getMessage());
    		                            }
    		                            break;
    		                        case 4:
    		                            voted = true;
    		                            break;
    		                        default:
    		                            System.out.println("Opção inválida.");
    		                            break;
    		                    }
    		                } while (!voted);
    		            }
    		        }
    		    } while (vChoice != 2);
    		    break;

    		case 5:
    			System.out.println("------------ RELATÓRIO ------------");
    			List<String> votacoes = Database.listVotesTables();
    			System.out.println("Lista de votações no histórico: ");
    			
    			for(int i = 0; i < votacoes.size(); i++) {
    				System.out.println(i + ". " + votacoes.get(i));
    			}
    			System.out.println("Qual votação você deseja ver o relatório: ");
    			Integer votacao = sc.nextInt();
    			
    			if(votacoes.get(votacao) != null) {
    				Voting.makeRelatorio(votacoes.get(votacao));
    			}
    			
        		break;
    		case 6:
    			System.out.println("Informe a quantidade de pessoas a ser adicionadas(min 500): ");
    			Integer qTy = sc.nextInt();
    			Database.seedDatabase(Math.max(qTy, 500));
    		}
    	}while(c0 != 7 ); // Close Menu

    	sc.close();
    }
}
