package com.jsy.eng_software_urna_eletronica.controllers;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Voting {

	Scanner sc = new Scanner(System.in);
	Integer id_candidate;
	Integer quantity;

	public Voting(){};

	public Voting(Integer id_candidate, Integer quantity) {
		this.id_candidate = id_candidate;
		this.quantity = quantity;
	}

	public static void startVoting() throws SQLException {
		
		List<String> tableNames = Database.listVotesTables();
		Database.initializeVoting(tableNames.size());		
		
	}
	
	public static void makeVote(Integer id) throws SQLException {
		if(Database.getCandidato(id) != null) {
			Database.makeVote(id);								
		}
	}
	
	public static void makeRelatorio(String votingTable) throws SQLException {
		System.out.println("Lista dos candidatos da " + votingTable);
		List<Candidato> entradas = Database.listVotacao(votingTable);
		
		for(Candidato candidato : entradas) {
			System.out.println(candidato.toString());			
		}
	
		System.out.println("\n------------------ ELEGIDOS ------------------");
		List<Candidato> elegidos = Database.getElected(votingTable);
		
		for(Candidato elegido : elegidos) {
			System.out.println(elegido.toString());
		}
	}

	public void Votacao() throws SQLException{
        int vChoice;
//        Voting.startVoting(); 
    		    do {
    		        System.out.println("\n------------ VOTAÇÃO ------------");
    		        System.out.println("1. Votar");
    		        System.out.println("2. Finalizar");
					System.out.println("-----------------------------------");
					System.out.print("Informe a opção desejada:");
    		        
    		        while (!sc.hasNextInt()) { // Prevenir erro com entradas inválidas
    		            System.out.println("Entrada inválida. Informe 1 para votar ou 2 para finalizar.");
    		            sc.next(); // Descarta entrada inválida
    		        }
    		        vChoice = sc.nextInt();

    		        if (vChoice == 1) {
    		            System.out.println("\n.........Processando.........");
						System.out.println("1. SENADOR     3. GOVERNADOR\n2. DEPUTADO    4. PRESIDENTE");
						System.out.println("\n.............................");
						System.out.print("Informe para qual cargo deseja votar:");

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
    		                    System.out.println("1. Próxima página (+1)   3. Votar");
    		                    System.out.println("2. Página anterior (-1)  4. Sair");

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
    		                            System.out.print("Informe o ID do candidato:");
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
        }

}
