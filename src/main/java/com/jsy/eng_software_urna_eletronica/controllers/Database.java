package com.jsy.eng_software_urna_eletronica.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import common.utils;

public class Database {

      Scanner sc = new Scanner(System.in);
    
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

        if (con != null) {
            String createTableSQL = String.format("""
                    CREATE TABLE IF NOT EXISTS voting_%d (
                        id_candidate INTEGER NOT NULL,
                        quantity INTEGER DEFAULT 0,
                        cargo VARCHAR(255) NOT NULL,
                        PRIMARY KEY (id_candidate, cargo)  -- Restriçāo de unicidade composta
                    );
                """, votingQuantity);

            Statement stmt = con.createStatement();
            stmt.execute(createTableSQL);
            System.out.println("Tabela Voting criada (ou já existente).");

            con.close();
        } else {
            System.out.println("Não foi possível inicializar o banco de dados.");
        }
    }
    
    public static List<String> listVotesTables() throws SQLException {
        List<String> tableNames = new ArrayList<>();
        String searchTableSQL = """
                SELECT table_name
                FROM information_schema.tables
                WHERE table_name LIKE 'voting\\_%'
                AND table_type = 'BASE TABLE'
                ORDER BY table_name;
                """;

        try (Connection con = getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(searchTableSQL)) {

            while (rs.next()) {
            	String name = rs.getString("table_name");
                tableNames.add(name);
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
                Candidato pessoa = utils.generateRandomUser();
                
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
    
    public static void insertUser(Candidato pessoa) {
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

            pstmt.setInt(1, id);

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

    public static Candidato getCandidato(Integer id) {
        String selectSQL = """
                SELECT * FROM users WHERE id = ?;
            """;

        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(selectSQL)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Candidato pessoa = new Candidato(rs.getInt("id"),rs.getString("nome"),rs.getString("partido"),rs.getString("cargo"));                
                return pessoa;
            } else {
                System.out.printf("Nenhum usuário encontrado com o ID %d.%n", id);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar o usuário no banco de dados: " + e.getMessage());
        }
        return null;
    }
    
    public static List<Candidato> getMultiplesUsers(List<Integer> ids) {
        List<Candidato> caditatsList = new ArrayList<>();
        
        if (ids == null || ids.isEmpty()) {
            System.out.println("A lista de IDs está vazia.");
            return caditatsList;
        }

        StringBuilder sqlBuilder = new StringBuilder("SELECT id, nome, partido, cargo FROM users WHERE id IN (");
        
        for (int i = 0; i < ids.size(); i++) {
            sqlBuilder.append("?");
            if (i < ids.size() - 1) {
                sqlBuilder.append(",");
            }
        }
        sqlBuilder.append(");");

        String selectSQL = sqlBuilder.toString();

        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(selectSQL)) {
            
            for (int i = 0; i < ids.size(); i++) {
                pstmt.setInt(i + 1, ids.get(i));
            }
            
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Candidato candidato = new Candidato(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("partido"),
                    rs.getString("cargo")
                );
                caditatsList.add(candidato);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar múltiplos usuários no banco de dados: " + e.getMessage());
        }

        return caditatsList;
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
 
                    User user = new Candidato(id, nome, partido, cargoResult);
                    candidates.add(user);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar candidatos: " + e.getMessage());
        }

        return candidates;
    }
     
    public static void editCandidato(Candidato pessoa) {
        // Atualizar os dados na tabela 'users'
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
                System.out.printf("\nUsuário com ID %d atualizado com sucesso! Linhas afetadas: %d%n", pessoa.getId(), rowsAffected);
            } else {
                System.out.printf("\nNenhum usuário encontrado com o ID %d.%n", pessoa.getId());
            }

            // Atualizar o cargo na tabela de votação mais recente
            // Localizando a tabela de votação mais recente
            List<String> tables = listVotesTables();
            if (tables.isEmpty()) {
                System.out.println("Nenhuma tabela de votação encontrada.");
                return;
            }

            String currentTable = tables.get(tables.size() - 1);  // Pega a tabela mais recente

            // Atualizando o cargo na tabela de votação
            String updateVotingSQL = String.format("""
                    UPDATE %s
                    SET cargo = ?
                    WHERE id_candidate = ?;
                """, currentTable);

            try (PreparedStatement pstmtVoting = con.prepareStatement(updateVotingSQL)) {
                pstmtVoting.setString(1, pessoa.getCargo());  // Novo cargo
                pstmtVoting.setInt(2, pessoa.getId());       // ID do candidato

                int votingRowsAffected = pstmtVoting.executeUpdate();
                if (votingRowsAffected > 0) {
                    System.out.printf("Cargo do candidato na tabela %s atualizado com sucesso! Linhas afetadas: %d%n", currentTable, votingRowsAffected);
                } else {
                    System.out.println("Nenhum cargo encontrado para o candidato na tabela de votação.");
                }
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


        String getCargoSQL = """
            SELECT cargo FROM users WHERE id = ?;
        """;

        String cargo = null;
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(getCargoSQL)) {

            pstmt.setInt(1, idCandidate);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                cargo = rs.getString("cargo");
            } else {
                System.out.println("Candidato não encontrado.");
                return;
            }
        }

        String updateSQL = String.format("""
                INSERT INTO %s (id_candidate, quantity, cargo)
                VALUES (?, 1, ?)
                ON CONFLICT (id_candidate, cargo)
                DO UPDATE SET quantity = %s.quantity + 1;
            """, currentTable, currentTable);

        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(updateSQL)) {

            pstmt.setInt(1, idCandidate);  // ID do candidato
            pstmt.setString(2, cargo);     // Cargo do candidato

            int rowsAffected = pstmt.executeUpdate();
            System.out.printf("Voto registrado com sucesso! Linhas afetadas: %d%n", rowsAffected);

        } catch (SQLException e) {
            System.err.println("Erro ao registrar o voto: " + e.getMessage());
            throw e; 
        }
    }


    
    public static List<Candidato> listVotacao(String votingTable) throws SQLException {        		
        String selectSQL = String.format("""
                SELECT v.id_candidate, v.quantity, u.nome, u.partido, u.cargo
                FROM %s v
                JOIN users u ON v.id_candidate = u.id;
            """, votingTable);
        
        List<Candidato> candidatos = new ArrayList<>();
        
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(selectSQL)) {
            
            try (ResultSet rs = pstmt.executeQuery()){
                while (rs.next()) {
                    Integer id_candidate = rs.getInt("id_candidate");
                    Integer quantity = rs.getInt("quantity");
                    String nome = rs.getString("nome");
                    String partido = rs.getString("partido");
                    String cargo = rs.getString("cargo");
                    
                    Candidato candidato = new Candidato(id_candidate, nome, partido, cargo);
                    candidato.setQuantity(quantity);  // Assuming there's a setQuantity method in Candidato class
                    
                    candidatos.add(candidato);
                }
                
                return candidatos;
            }
        } catch(SQLException e) {
            System.err.println("Erro ao pegar as entradas da votação: " + e.getMessage());
            throw e; 
        }    		
    }
    
    
    public static List<Candidato> getElected(String votingTable) throws SQLException {
        List<Candidato> electedCandidates = new ArrayList<>();
        
        // First, get the positions (cargos) available in the voting table
        String cargosSQL = String.format(
            "SELECT DISTINCT cargo FROM %s;", 
            votingTable
        );
        
        try (Connection con = getConnection();
             PreparedStatement cargoStmt = con.prepareStatement(cargosSQL)) {
            
            ResultSet cargoRs = cargoStmt.executeQuery();
            
            // For each position (cargo)
            while (cargoRs.next()) {
                String cargo = cargoRs.getString("cargo");
                
                // SQL to get the candidate with highest votes for this position
                String selectSQL = String.format(
                    "SELECT id_candidate, SUM(quantity) AS total_votes " +
                    "FROM %s " +
                    "WHERE cargo = ? " +
                    "GROUP BY id_candidate " +
                    "ORDER BY total_votes DESC " +
                    "LIMIT 1;", 
                    votingTable
                );
                
                try (PreparedStatement pstmt = con.prepareStatement(selectSQL)) {
                    pstmt.setString(1, cargo);
                    ResultSet rs = pstmt.executeQuery();
                    
                    if (rs.next()) {
                        Integer idCandidate = rs.getInt("id_candidate");
                        Integer totalVotes = rs.getInt("total_votes");
                        
                        // Query to get candidate data
                        String selectCandidateSQL = 
                            "SELECT id, nome, partido, cargo " +
                            "FROM users " +
                            "WHERE id = ?;";
                        
                        try (PreparedStatement candidateStmt = con.prepareStatement(selectCandidateSQL)) {
                            candidateStmt.setInt(1, idCandidate);
                            ResultSet candidateRs = candidateStmt.executeQuery();
                            
                            if (candidateRs.next()) {
                                Candidato candidate = new Candidato(
                                    candidateRs.getInt("id"),
                                    candidateRs.getString("nome"),
                                    candidateRs.getString("partido"),
                                    candidateRs.getString("cargo")
                                );
                                
                                // Set votes to the candidate
                                candidate.setQuantity(totalVotes);
                                
                                electedCandidates.add(candidate);
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar os candidatos mais votados: " + e.getMessage());
            throw e;
        }
        
        return electedCandidates;
    }
    
    
}
