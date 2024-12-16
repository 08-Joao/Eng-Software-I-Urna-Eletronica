/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.jsy.eng_software_urna_eletronica;


import java.sql.SQLException;
import java.util.Scanner;

import com.jsy.eng_software_urna_eletronica.controllers.Database;
import com.jsy.eng_software_urna_eletronica.controllers.Functions;

/**
 *
 * @author Joao
 */
public class Eng_software_urna_eletronica {

    public static void main(String[] args) throws SQLException {
		Functions func = new Functions();
		Database db = new Database();
    	Scanner sc = new Scanner(System.in);
    	
    	int c0 = -1;
    	
    	do {
			func.ExibeMenu();
			System.out.print("Selecione a opção desejada (1-7): ");
    		c0 = sc.nextInt();
    		
    		switch(c0) {
    		case 1:
    			func.Cadastro();
					break;
    		case 2:
    			func.Remover();
					break;
    		case 3:
    			func.Editar();
					break;
    		case 4:
    		    func.Votacao();
					break;
    		case 5:
    			func.Relatorio();
        			break;
    		case 6:
    			func.PopularBD();
    		}
    	}while(c0 != 7 ); // Close Menu
    }
}
