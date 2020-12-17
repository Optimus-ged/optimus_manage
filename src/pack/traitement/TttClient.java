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
public class TttClient {

    private String nom, prenom, sexe, telephone;
    private int id;

    public TttClient(String nom, String prenom, String sexe, String telephone, int id) {
        this.nom = nom;
        this.prenom = prenom;
        this.sexe = sexe;
        this.telephone = telephone;
        this.id = id;
    }

    public TttClient() {
    }

    public void client(int btn) throws ClassNotFoundException, SQLException {
        if (btn == 1) {
            PreparedStatement ps = MdlConnexion.getCnx().prepareCall("{Call sp_client_in(?,?,?,?,?)}");
            ps.setInt(1, id);
            ps.setString(2, nom);
            ps.setString(3, prenom);
            ps.setString(4, sexe);
            ps.setString(5, telephone);
            ps.executeUpdate();
        } else if (btn == 2) {
            PreparedStatement ps = MdlConnexion.getCnx().prepareCall("{Call sp_client_del(?)}");
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public String requette() {
        return "SELECT * FROM client";
    }
}
