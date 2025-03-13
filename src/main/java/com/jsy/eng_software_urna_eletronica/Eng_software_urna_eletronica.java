package com.jsy.eng_software_urna_eletronica;

import java.sql.SQLException;
import com.jsy.eng_software_urna_eletronica.controllers.User;

public class Eng_software_urna_eletronica {

    public static void main(String[] args) throws SQLException {
        User us = new User();
        
        do {
            User selectedUser = us.escolheTipoUsuario(us);
            if (selectedUser != null) {
                us.verificaTipoUsuario(selectedUser);
            }
        } while(true); // The loop will break when user chooses to exit
    }
}
