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
public class TttAgent {
    private String nom, prenom, postnom, sexe, post;
    private int id;

    public TttAgent(String nom, String prenom, String postnom, String sexe, String post, int id) {
        this.nom = nom;
        this.prenom = prenom;
        this.postnom = postnom;
        this.sexe = sexe;
        this.post = post;
        this.id = id;
    }

    public TttAgent() {
        
    }
    
    public void agent(int btn)throws ClassNotFoundException, SQLException{
        if(btn == 1){
            PreparedStatement ps = MdlConnexion.getCnx().prepareCall("{Call sp_agent_in(?,?,?,?,?,?)}");
            ps.setInt(1,id);
            ps.setString(2, nom);
            ps.setString(3, prenom);
            ps.setString(4, postnom);
            ps.setString(5, sexe);
            ps.setString(6, post);
            ps.executeUpdate();
        }
        else if(btn == 2){
            PreparedStatement ps = MdlConnexion.getCnx().prepareCall("{Call sp_agent_del(?)}");
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    
    public String requette(){
        return "SELECT * FROM agent";
    }
}
