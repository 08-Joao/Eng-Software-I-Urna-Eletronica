package com.jsy.eng_software_urna_eletronica.controllers;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
                        nome VARCHAR(50) NOT NULL UNIQUE,                        
                        partido VARCHAR(100) NOT NULL,
                        cargo VARCHAR(50) NOT NULL                        
                    );
                """;
            
            Statement stmt = con.createStatement();
            stmt.execute(createTableSQL);
            
            System.out.println("Tabela users criada (ou já existente).");
            
    	}else {
    		System.out.println("Não foi possível inicializar o banco de dados.");
    	}
    	
    }
    
}
