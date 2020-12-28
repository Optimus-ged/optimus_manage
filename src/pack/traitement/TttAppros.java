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
import pack.model.MdlEnteteAppro;

/**
 *
 * @author Optimus
 */
public class TttAppros {

    private PreparedStatement preparedStatemant;
    private static TttAppros _instance;

    public static TttAppros getInstance() {
        if (_instance == null) {
            _instance = new TttAppros();
        }
        return _instance;
    }

    public boolean isSave(Object ob) throws SQLException, ClassNotFoundException {
        if (ob instanceof MdlEnteteAppro) {
            MdlEnteteAppro enteteApp = (MdlEnteteAppro) ob;
            preparedStatemant = MdlConnexion.getCnx().prepareCall("Call sp_enteteAppro (?)");
            preparedStatemant.setString(1, enteteApp.getNom());
            int x = preparedStatemant.executeUpdate();
            if (x != 0) {
                return true;
            }
        }else if(ob instanceof MdlDetailsAppro){
            MdlDetailsAppro detAppros = (MdlDetailsAppro) ob;
            preparedStatemant = MdlConnexion.getCnx().prepareCall("Call sp_apptoDetail_in (?,?,?)");
            preparedStatemant.setString(1, detAppros.getDesiProduit());
            preparedStatemant.setFloat(2, detAppros.getQte());
            preparedStatemant.setInt(3, detAppros.getId());
            int x = preparedStatemant.executeUpdate();
            if (x != 0) {
                return true;
            }
        }
        return false;
    }
}
