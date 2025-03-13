package com.jsy.eng_software_urna_eletronica.controllers;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Functions {
    Scanner sc = new Scanner(System.in);
    
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

    public void Imprimir() throws SQLException {
        System.out.println("\n-------------------- LISTA DE CANDIDATOS -----------------");
        System.out.println("1. CIVIL");
        System.out.println("2. SENADOR");
        System.out.println("3. DEPUTADO");
        System.out.println("4. GOVERNADOR");
        System.out.println("5. PRESIDENTE");
        System.out.println("6. Voltar");
        System.out.println("------------------------------------------------");
        System.out.print("Escolha o cargo para listar: ");
        
        int choice = sc.nextInt();
        if (choice == 6) return;

        String cargo = switch (choice) {
            case 1 -> "CIVIL";
            case 2 -> "SENADOR";
            case 3 -> "DEPUTADO";
            case 4 -> "GOVERNADOR";
            case 5 -> "PRESIDENTE";
            default -> {
                System.out.println("Opção inválida");
                yield null;
            }
        };

        if (cargo == null) return;

        int currentPage = 1;
        int pageSize = 50;
        boolean running = true;

        while (running) {
            System.out.println("\n-------------------- PÁGINA " + currentPage + " -----------------");
            List<User> candidates = Database.listCandidates(cargo, (currentPage - 1) * pageSize);
            
            if (candidates.isEmpty()) {
                System.out.println("Nenhum candidato encontrado.");
                break;
            }

            for (User candidate : candidates) {
                if (candidate instanceof Candidato) {
                    Candidato c = (Candidato) candidate;
                    System.out.printf("ID: %d | Nome: %s | Partido: %s | Cargo: %s%n", 
                        c.getId(), c.getNome(), c.getPartido() != null ? c.getPartido() : "N/A", c.getCargo());
                }
            }

            System.out.println("\n1. Próxima página");
            System.out.println("2. Página anterior");
            System.out.println("3. Voltar ao menu");
            System.out.print("Escolha uma opção: ");
            
            int navChoice = sc.nextInt();
            switch (navChoice) {
                case 1 -> currentPage++;
                case 2 -> {
                    if (currentPage > 1) currentPage--;
                    else System.out.println("Já está na primeira página.");
                }
                case 3 -> running = false;
                default -> System.out.println("Opção inválida");
            }
        }
    }
}
