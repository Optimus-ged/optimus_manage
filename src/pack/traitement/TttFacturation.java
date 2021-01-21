/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack.traitement;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import pack.model.MdlConnexion;
import pack.model.MdlDetailsAppro;
import pack.model.MdlEnteteFacture;

/**
 *
 * @author Optimus
 */
public class TttFacturation {
    private PreparedStatement preparedStatemant;
    private static TttFacturation _instance;

    public static TttFacturation getInstance() {
        if (_instance == null) {
            _instance = new TttFacturation();
        }
        return _instance;
    }
    
    public boolean isSave2(Object ob) throws SQLException, ClassNotFoundException {
        if (ob instanceof MdlEnteteFacture) {
            MdlEnteteFacture enteteFact = (MdlEnteteFacture) ob;
            preparedStatemant = MdlConnexion.getCnx().prepareCall("Call sp_enteteFacture (?)");
            preparedStatemant.setString(1, enteteFact.getNom());
            int x = preparedStatemant.executeUpdate();
            if (x != 0) {
                return true;
            }
        }else if(ob instanceof MdlDetailsAppro){
            MdlDetailsAppro detFact = (MdlDetailsAppro) ob;
            preparedStatemant = MdlConnexion.getCnx().prepareCall("Call sp_factDetail_in (?,?,?)");
            preparedStatemant.setString(1, detFact.getDesiProduit());
            preparedStatemant.setFloat(2, detFact.getQte());
            preparedStatemant.setInt(3, detFact.getId());
            int x = preparedStatemant.executeUpdate();
            if (x != 0) {
                return true;
            }
        }
        return false;
    }
}
