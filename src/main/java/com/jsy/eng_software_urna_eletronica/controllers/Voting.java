package com.jsy.eng_software_urna_eletronica.controllers;
import java.sql.SQLException;
import java.util.List;

public class Voting {
    public static void startVoting() throws SQLException {
        List<String> tableNames = Database.listVotesTables();
        
        // If no voting tables exist, start with the first voting table
        int tableCount = tableNames.isEmpty() ? 0 : tableNames.size();
        Database.initializeVoting(tableCount);
    }

    public static void makeVote(Integer id) throws SQLException {
        User user = Database.getUser(id);
        if (user != null && !user.getCargo().equalsIgnoreCase("CIVIL")) {
            Database.makeVote(id);
        } else {
            throw new SQLException("Invalid candidate. Only non-CIVIL users can be voted for.");
        }
    }

    public static void makeRelatorio(String votingTable) throws SQLException {
        if (votingTable == null || votingTable.isEmpty()) {
            System.out.println("Invalid voting table selected.");
            return;
        }

        System.out.println("Lista dos candidatos da " + votingTable);
        List<User> entradas = Database.listVotacao(votingTable);

        // Print all candidates
        if (entradas.isEmpty()) {
            System.out.println("Nenhum candidato encontrado.");
        } else {
            for (User candidato : entradas) {
                System.out.println(candidato);
            }
        }

        System.out.println("\n------------------ ELEITOS ------------------");
        List<User> elegidos = Database.getElected(votingTable);

        // Print elected candidates, excluding CIVIL candidates
        boolean hasElected = false;
        for (User elegido : elegidos) {
            if (!elegido.getCargo().equalsIgnoreCase("CIVIL")) {
                System.out.println(elegido);
                hasElected = true;
            }
        }

        if (!hasElected) {
            System.out.println("Nenhum candidato eleito encontrado.");
        }
    }
}