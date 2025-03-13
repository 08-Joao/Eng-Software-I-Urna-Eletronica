package com.jsy.eng_software_urna_eletronica.controllers;

import java.sql.SQLException;

public class Adm extends User {

    Voting vt = new Voting();
    private String nomeDeUsuario;
    private String senha;

    public Adm(){}

    public Adm(Integer id, String nome, String nomeDeUsuario, String senha) {
        super(id, nome);
        this.nomeDeUsuario = nomeDeUsuario;
        this.senha = senha;
    }

    // Getters e Setters
    public String getNomeDeUsuario() {
        return nomeDeUsuario;
    }

    public void setNomeDeUsuario(String nomeDeUsuario) {
        this.nomeDeUsuario = nomeDeUsuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void verificaUsuarioSenha(String usuario, String password){
        if (nomeDeUsuario == null || !nomeDeUsuario.equals(usuario)) {
            System.out.println("Nome de usuário inválido");
            return;
        }

        if (senha == null || !senha.equals(password)) {
            System.out.println("Senha inválida");
            return;
        }

        System.out.println("Login realizado com sucesso!");
    }

    public int MenuAdm() throws SQLException{
        int c0;
        do {
            System.out.println("\n-------------------- MENU ADMINISTRADOR -----------------");
            System.out.println("1. Cadastrar usuário");
            System.out.println("2. Remover usuário");
            System.out.println("3. Editar usuário");
            System.out.println("4. Iniciar votação");
            System.out.println("5. Gerar relatório");
            System.out.println("6. Popular banco de dados");
            System.out.println("7. Listar candidatos");
            System.out.println("8. Sair");
            System.out.println("------------------------------------------------");
            System.out.print("Escolha uma opção: ");
            c0 = sc.nextInt();

            switch(c0) {
                case 1:
                    Cadastro();
                    break;
                case 2:
                    Remover();
                    break;
                case 3:
                    Editar();
                    break;
                case 4:
                    vt.Votacao();
                    break;
                case 5:
                    Functions func = new Functions();
                    func.Relatorio();
                    break;
                case 6:
                    Functions func2 = new Functions();
                    func2.PopularBD();
                    break;
                case 7:
                    Functions func3 = new Functions();
                    func3.Imprimir();
                    break;
                case 8:
                    System.out.println("Operação encerrada com exito!");
                    break;
                default:
                    System.out.println("Valor inválido");
            }
        } while(c0 != 8);
        return c0;
    }

    public void admLogin(){
        System.out.print("------------------ MENU ------------------ ");
        System.out.print("\nInforme o nome de usuário: ");
        String userName = sc.nextLine();
        System.out.print("\nInforme a senha : ");
        String userPassword = sc.nextLine();
        verificaUsuarioSenha(userName, userPassword);
    }
}