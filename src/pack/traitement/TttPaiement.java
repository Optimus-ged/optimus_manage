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
public class TttPaiement {

    private int idFacture;
    private float montant;

    public TttPaiement(int idFacture, float montant) {
        this.idFacture = idFacture;
        this.montant = montant;
    }

    public TttPaiement() {

    }

    public void paiement(int btn) throws ClassNotFoundException, SQLException {
        if (btn == 1) {
            PreparedStatement ps = MdlConnexion.getCnx().prepareCall("{Call sp_paiement_in(?,?)}");
            ps.setInt(1, idFacture);
            ps.setFloat(2, montant);
            ps.executeUpdate();
        }
    }

    public String requette() {
        return "SELECT * FROM paiement";
    }
}
