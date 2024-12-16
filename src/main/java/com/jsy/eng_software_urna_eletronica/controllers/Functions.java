package com.jsy.eng_software_urna_eletronica.controllers;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import com.jsy.eng_software_urna_eletronica.controllers.User;
import com.jsy.eng_software_urna_eletronica.controllers.Voting;
import java.sql.Connection;
import java.util.List;


public class Functions {
    Scanner sc = new Scanner(System.in);

    public void ExibeMenu(){
            System.out.println("\n-------------------- MENU --------------------");
    		System.out.println("1. Cadastrar Pessoa    5. Relatório de votação\n2. Remover Pessoa      6. Popular base de dados");
			System.out.println("3. Editar Pessoa       7. Sair\n4. Iniciar Votação");
    		System.out.println("----------------------------------------------");
    }

    public void Cadastro(){
        System.out.println("\n--------------- CADASTRO -----------------");
                System.out.print("Informe o nome do usuário:");
                String nome = sc.nextLine();
                            
                System.out.println("\n1. CIVIL       4. GOVERNADOR\n2. DEPUTADO    5. PRESIDENTE\n3. SENADOR");
                System.out.println("------------------------------------------");
                System.out.print("Informe o cargo do usuario: ");
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
                    System.out.print("Informe o partido do usuário:");
                    String partido = sc.nextLine();
                    user = new User(0, nome, partido,Cargo);
                } else {
                    user = new User(0, nome, null, "CIVIL");
                }
                Database.insertUser(user);
        }

        public void Remover(){
            System.out.println("\n------------ REMOÇÃO ------------");
            System.out.print("Informe o ID do usuário a ser removido: ");
            Integer id = sc.nextInt();
            Database.removeUser(id);
        }

        public void Editar(){
            System.out.println("\n------------ EDIÇÃO -------------");
                System.out.print("Informe o id do usuário a ser editado: ");
                Integer eId = sc.nextInt();
                
                User foundUser = Database.getUser(eId);
                if(foundUser != null) {
                    int eChoice = -1;
                    
                    String eNome = foundUser.getNome();
                    String ePartido = foundUser.getPartido();
                    String eCargo = foundUser.getCargo();
                    do {
                        System.out.println("\n..........Editando..........");
                        System.out.print("1. Nome     3. Partido\n2. Cargo    4. Finalizar");
                        System.out.println("\n............................");
                        System.out.print("Informe o que deseja editar: ");
                        eChoice = sc.nextInt();
                        switch(eChoice) {
                        case 1: 
                            sc.nextLine();
                            System.out.print("Informe o novo nome do usuário: ");
                            eNome = sc.nextLine();
                            break;
                        case 2:
                            if(ePartido == null || ePartido.trim().isEmpty()) {
                                ePartido = null;
                                System.out.print("Informe novo partido do usuário: ");
                                sc.nextLine();
                                ePartido = sc.nextLine();
                                
                                Boolean validChoice = false;
                                do {
                                    System.out.println("\n..........Editando..........");      			    	        
                                    System.out.println("1. SENADOR     3. GOVERNADOR\n2. DEPUTADO    4. PRESIDENTE");
                                    System.out.println("\n............................");
                                    System.out.print("Informe o novo cargo: ");  
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
                                System.out.print("Informe o novo partido do usuário: ");
                                sc.nextLine();
                                ePartido = sc.nextLine();
                            }
                            break;
                        case 3:
                            Boolean isCivil = eCargo.equalsIgnoreCase("CIVIL");
                            Boolean validChoice2 = false;
                            
                            do {
                                System.out.println("\n..........Editando..........");     
                                System.out.print("1. CIVIL        4. GOVERNADOR\n2. SENADOR      5. PRESIDENTE\n3. DEPUTADO");
                                System.out.println("\n..........................."); 
                                System.out.print("Informe o novo cargo do usuario: ");  
                                int cargoChoice2 = sc.nextInt();
                                sc.nextLine();
                                
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
                                System.out.print("Informe o novo partido do usuário: ");
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
        }

        public void Votacao() throws SQLException{
            int vChoice;
    		    Voting.startVoting(); 
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
    
        public void Relatorio() throws SQLException{
            System.out.println("\n-------------------- RELATÓRIO -----------------");
    			List<String> votacoes = Database.listVotesTables();
    			System.out.println("Lista de votações no histórico: ");
    			
    			for(int i = 1; i <= votacoes.size()-1; i++) {
    				System.out.println(i + ". " + votacoes.get(i));
    			}
				System.out.println("------------------------------------------------");
    			System.out.print("Qual votação você deseja ver o relatório: ");
    			Integer votacao = sc.nextInt();
    			
    			if(votacoes.get(votacao) != null) {
    				Voting.makeRelatorio(votacoes.get(votacao));
    			}	
        }

        public void PopularBD() throws SQLException{
            System.out.print("Informe a quantidade de pessoas a ser adicionadas(min 500): ");
    			Integer qTy = sc.nextInt();
    			Database.seedDatabase(Math.max(qTy, 500));
        }
}
