/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack.traitement;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import pack.model.MdlConnexion;

/**
 *
 * @author Optimus
 */
public class TttFsseur {
    private String nom, addresse, telephone;
    private int id;
    
    public TttFsseur(){
        
    }
    
    public TttFsseur(int id, String addresse, String telephone){
        this.id = id;
        this.addresse = addresse;
        this.telephone = telephone;
    }
    
     public void fournisseur(int btn) throws ClassNotFoundException, SQLException {
        if (btn == 1) {
            PreparedStatement ps = MdlConnexion.getCnx().prepareCall("{Call sp_fournisseur_in(?,?,?,?)}");
            ps.setInt(1, id);
            ps.setString(2, nom);
            ps.setString(3, addresse);
            ps.setString(3, telephone);
            ps.executeUpdate();
        } else if (btn == 2) {
//            PreparedStatement ps = MdlConnexion.getCnx().prepareCall("{Call sp_client_del(?)}");
//            ps.setInt(1, id);
//            ps.executeUpdate();
        }
    }

    public String requette() {
        return "SELECT * FROM fourniseur";
    }
}
