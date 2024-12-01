/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.jsy.eng_software_urna_eletronica;

import java.sql.Connection;
import java.sql.SQLException;

import com.jsy.eng_software_urna_eletronica.controllers.Database;
import com.jsy.eng_software_urna_eletronica.controllers.User;
import com.jsy.eng_software_urna_eletronica.controllers.Voting;

/**
 *
 * @author Joao
 */
public class Eng_software_urna_eletronica {

    public static void main(String[] args) throws SQLException {
    	Database db = new Database();
//    	Database.seedDatabase(500);
    	
    	User teste = new User(0,"Jhony","PTsOL","PRESIDENTE");
    	Database.insertUser(teste);
    	Database.removeUser(499);
    	
    }
}
