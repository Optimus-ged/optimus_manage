/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack.model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Optimus
 */
public class MdlLogin {
    private String nom, pass;

    public MdlLogin(String nom, String pass) {
        this.nom = nom;
        this.pass = pass;
    }
   public MdlLogin(){}

    public boolean tesLogin(String nom,String prenom) throws ClassNotFoundException, SQLException {
        boolean bool = false;
        String rq = "SELECT nom, prenom FROM agent WHERE nom = '" + nom + "' AND prenom = '" + prenom + "'";
        ResultSet rs = MdlConnexion.getCnx().createStatement().executeQuery(rq);
        if(rs.next()){            
          return true;
        }
        return false;     
    }
}
