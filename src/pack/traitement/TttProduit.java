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
public class TttProduit {
    private String designation;
    private float pu;
    private int id;
    
    public TttProduit(String designation, float pu, int id){
        this.designation = designation;
        this.pu = pu;
        this.id = id;
    }
    
    public TttProduit(){
        
    }
    
    public void produit(int btn) throws ClassNotFoundException, SQLException {
        if (btn == 1) {
            PreparedStatement ps = MdlConnexion.getCnx().prepareCall("{Call sp_produit_in(?,?,?)}");
            ps.setInt(1, id);
            ps.setString(2, designation);
            ps.setFloat(3, pu);
            ps.executeUpdate();
        } else if (btn == 2) {
//            PreparedStatement ps = MdlConnexion.getCnx().prepareCall("{Call sp_client_del(?)}");
//            ps.setInt(1, id);
//            ps.executeUpdate();
        }
    }

    public String requette() {
        return "SELECT * FROM produit";
    }
}
