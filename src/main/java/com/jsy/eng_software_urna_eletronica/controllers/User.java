package com.jsy.eng_software_urna_eletronica.controllers;

import java.sql.SQLException;
import java.util.Scanner;

public class User {
    
//    Adm adm = new Adm(); 
    Scanner sc = new Scanner(System.in);

    protected Integer id;
    protected String nome;

    // Construtor padrão
    public User() {
    }

    // Construtor com parâmetros
    public User(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
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

    // Método para mostrar informações do usuário
    public void mostrarInfo() {
        System.out.println("ID: " + id);
        System.out.println("Nome: " + nome);
    }

    public void MenuUser(){

        int c0 = -1;

        System.out.println("\n-------------------- MENU --------------------");
        System.out.println("1. Votar    2. Sair\n");
        System.out.println("------------------------------------------------");
        System.out.print("Selecione a opção desejada (1-7): ");
        c0 = sc.nextInt();
    		switch(c0) {
    		    case 1:
    			    Cadastro();
					    break;
    		    case 2:
                    System.out.println("Operação encerrada com exito!");
    			    return;
                default:
                    System.out.println("Opção inválida");
            }
    }

    public void verificaTipoUsuario(User user) throws SQLException {
        if (user instanceof Adm) {
            Adm adm = (Adm) user;  // Cast the user to Adm
            adm.admLogin();
            int c0;
            do {
                c0 = adm.MenuAdm();
            } while (c0 != 7);  
        } else if(user instanceof Candidato) {
            MenuUser();
        } else {
            System.out.println("Esse usuário não existe");
        }
    }
        
        
    public User escolheTipoUsuario(User user){
        System.out.print("------- Escolha o nível de acesso ------- ");
        System.out.println("\n1. Administrador       2. Candidato\n3. Sair");
        System.out.println("----------------------------------------- ");
        System.out.print("Escolha: ");
        int ch = sc.nextInt(); 
        switch (ch) {
            case 1:
                return new Adm();      
            case 2:
                return new Candidato();
            case 3:
                System.out.println("Saindo...");
                System.exit(0);
            default:
                System.out.print("Escolha uma opção válida");
                return null;
        }
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

                Candidato candidato = null;
                if (isVotavel) {
                    System.out.print("Informe o partido do usuário:");
                    String partido = sc.nextLine();
                    candidato = new Candidato(0, nome, partido,Cargo);
                } else {
                    candidato = new Candidato(0, nome, null, "CIVIL");
                }
                Database.insertUser(candidato);
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
            
            Candidato foundUser = Database.getCandidato(eId);
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
                
                Candidato editedCandidato = new Candidato(eId,eNome,ePartido,eCargo);
                Database.editCandidato(editedCandidato);
            }
    }

}
