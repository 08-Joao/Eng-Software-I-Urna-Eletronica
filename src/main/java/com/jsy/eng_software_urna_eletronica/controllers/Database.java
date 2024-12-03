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

    public static User getUser(Integer id) {
        String selectSQL = """
                SELECT * FROM users WHERE id = ?;
            """;

        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(selectSQL)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                User pessoa = new User(rs.getInt("id"),rs.getString("nome"),rs.getString("partido"),rs.getString("cargo"));                
                return pessoa;
            } else {
                System.out.printf("Nenhum usuário encontrado com o ID %d.%n", id);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar o usuário no banco de dados: " + e.getMessage());
        }
        return null;
    }

    
    public static Integer getPagesNumber(String cargo) {
        String countSQL = """
                SELECT COUNT(*) AS total 
                FROM users 
                WHERE cargo = ?;
            """;

        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(countSQL)) {

            pstmt.setString(1, cargo.toUpperCase());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int totalCandidates = rs.getInt("total");

                    return (int) Math.ceil(totalCandidates / 100.0);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao obter número de páginas: " + e.getMessage());
        }

        return 0;
    }

    
    public static List<User> listCandidates(String cargo, Integer offset) {
        String selectSQL = """
                SELECT id, nome, partido, cargo 
                FROM users 
                WHERE cargo = ? 
                LIMIT 100 OFFSET ?;
            """;

        List<User> candidates = new ArrayList<>();

        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(selectSQL)) {


            pstmt.setString(1, cargo.toUpperCase());
            pstmt.setInt(2, offset);

            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    Integer id = rs.getInt("id");
                    String nome = rs.getString("nome");
                    String partido = rs.getString("partido");
                    String cargoResult = rs.getString("cargo");
 
                    User user = new User(id, nome, partido, cargoResult);
                    candidates.add(user);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar candidatos: " + e.getMessage());
        }

        return candidates;
    }
    
    
    public static void editUser(User pessoa) {
        String updateSQL = """
                UPDATE users SET nome = ?, partido = ?, cargo = ? WHERE id = ?;
            """;

        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(updateSQL)) {

            pstmt.setString(1, pessoa.getNome());
            pstmt.setString(2, pessoa.getPartido());
            pstmt.setString(3, pessoa.getCargo());
            pstmt.setInt(4, pessoa.getId());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.printf("Usuário com ID %d atualizado com sucesso! Linhas afetadas: %d%n", pessoa.getId(), rowsAffected);
            } else {
                System.out.printf("Nenhum usuário encontrado com o ID %d.%n", pessoa.getId());
            }

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar o usuário no banco de dados: " + e.getMessage());
        }
    }

    
    public static void makeVote(Integer idCandidate) throws SQLException {
        List<String> tables = listVotesTables();
        if (tables.isEmpty()) {
            System.out.println("Nenhuma tabela de votação encontrada.");
            return;
        }

        String currentTable = tables.get(tables.size() - 1); 

        String updateSQL = String.format("""
                INSERT INTO %s (id_candidate, quantity)
                VALUES (?, 1)
                ON CONFLICT (id_candidate)
                DO UPDATE SET quantity = %s.quantity + 1;
            """, currentTable, currentTable);

        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(updateSQL)) {

            pstmt.setInt(1, idCandidate);

            int rowsAffected = pstmt.executeUpdate();
            System.out.printf("Voto registrado com sucesso! Linhas afetadas: %d%n", rowsAffected);

        } catch (SQLException e) {
            System.err.println("Erro ao registrar o voto: " + e.getMessage());
            throw e; 
        }
    }

    
}
