package com.jsy.eng_software_urna_eletronica.controllers;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import common.utils;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Joao
 */
public class Database {
    
	public Database() throws SQLException{
		initializeUsers();
	}
	
	private static Connection getConnection() {
        // URL, usuário e senha do banco de dados
        String url = "jdbc:postgresql://ep-round-dust-a5gw1o2x.us-east-2.aws.neon.tech/Urna%20eletr%C3%B4nica?user=Urna%20eletr%C3%B4nica_owner&password=X6xfC4EpSRBA&sslmode=require";


        try {
            // Carrega o driver do PostgreSQL (opcional em versões modernas do Java)
            Class.forName("org.postgresql.Driver");


            return DriverManager.getConnection(url);

        } catch (ClassNotFoundException e) {
            System.out.println("Driver do PostgreSQL não encontrado.");
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados.");
        }

        // Retorna nulo se a conexão falhar
        return null;
    }
    
    
    
    
    private static void initializeUsers() throws SQLException {
    	
    	Connection con = getConnection();
    	
    	if(con != null) {
            String createTableSQL = """
                    CREATE TABLE IF NOT EXISTS users (
                        id SERIAL PRIMARY KEY NOT NULL UNIQUE,
                        nome VARCHAR(50) NOT NULL,                        
                        partido VARCHAR(100),
                        cargo VARCHAR(50) NOT NULL                        
                    );
                """;
            
            Statement stmt = con.createStatement();
            stmt.execute(createTableSQL);
            
            System.out.println("Tabela Users criada (ou já existente).");
            con.close();
    	}else {
    		System.out.println("Não foi possível inicializar o banco de dados.");
    	}
    	
    }
    
    public static void initializeVoting(Integer votingQuantity) throws SQLException {
    	Connection con = getConnection();
    	
    	if(con != null) {
    		String createTableSQL = String.format("""
    			    CREATE TABLE IF NOT EXISTS voting_%d (
    			        id_candidate INTEGER PRIMARY KEY NOT NULL UNIQUE,
    			        quantity INTEGER
    			    );
    			""", votingQuantity);

    		
    		
            Statement stmt = con.createStatement();
            stmt.execute(createTableSQL);
            System.out.println("Tabela Voting criada (ou já existente).");
            con.close();
    	}else {
    		System.out.println("Não foi possível inicializar o banco de dados.");
    	}
    }
    
    public static List<String> listVotesTables() throws SQLException {
        List<String> tableNames = new ArrayList<>();
        String searchTableSQL = """
            SELECT table_name
            FROM information_schema.tables
            WHERE table_name LIKE 'voting_%'
              AND table_type = 'BASE TABLE'
            ORDER BY table_name
        """;

        try (Connection con = getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(searchTableSQL)) {
 
            while (rs.next()) {
                tableNames.add(rs.getString("table_name"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar tabelas de votos: " + e.getMessage());
            throw e; 
        }

        return tableNames;
    }
    
    
    public static void seedDatabase(Integer quantity) throws SQLException {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser um número positivo.");
        }

        String insertSQL = """
            INSERT INTO users (nome, partido, cargo) VALUES (?, ?, ?);
        """;

        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(insertSQL)) {

            for (int i = 0; i < quantity; i++) {
                User pessoa = utils.generateRandomUser();
                
                pstmt.setString(1, pessoa.getNome());
                pstmt.setString(2, pessoa.getPartido());
                pstmt.setString(3, pessoa.getCargo());              
                pstmt.addBatch();
            }

            int[] result = pstmt.executeBatch();
            System.out.println(result.length + " registros inseridos com sucesso.");
            
            con.close();
        } catch (SQLException e) {
            System.err.println("Erro ao popular o banco de dados: " + e.getMessage());
            throw e;
        }
    }

    
    public static void insertUser(User pessoa) {
        String insertSQL = """
                INSERT INTO users (nome, partido, cargo) VALUES (?, ?, ?);
            """;

        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(insertSQL)) {

            pstmt.setString(1, pessoa.getNome());
            pstmt.setString(2, pessoa.getPartido());
            pstmt.setString(3, pessoa.getCargo());

            int rowsAffected = pstmt.executeUpdate();
            System.out.printf("Usuário inserido com sucesso! Linhas afetadas: %d%n", rowsAffected);

        } catch (SQLException e) {
            System.err.println("Erro ao inserir o usuário no banco de dados: " + e.getMessage());
        }
    }
    
    public static void removeUser(Integer id) {
        String removeSQL = """
                DELETE FROM users WHERE id = ?;
            """;

        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(removeSQL)) {

            // Define o parâmetro para a instrução preparada
            pstmt.setInt(1, id);

            // Executa a instrução e obtém o número de linhas afetadas
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.printf("Usuário com ID %d removido com sucesso! Linhas afetadas: %d%n", id, rowsAffected);
            } else {
                System.out.printf("Nenhum usuário encontrado com o ID %d.%n", id);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao remover o usuário do banco de dados: " + e.getMessage());
        }
    }


    
}
