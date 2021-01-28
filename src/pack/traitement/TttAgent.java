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
    private String nom, prenom,sexe, telephone,  post;
    private int id;

    public TttAgent(int id, String nom, String prenom,String sexe, String telephone, String post) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
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
            ps.setString(4, sexe);
            ps.setString(5, telephone);
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
