/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack.traitement;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import pack.model.MdlConnexion;
import pack.model.MdlDetailFacture;
import pack.model.MdlDetailsAppro;
import pack.model.MdlEnteteAppro;
import pack.model.MdlEnteteFacture;

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

    public boolean isSave(Object ob, int btn) throws SQLException, ClassNotFoundException {
        if (btn == 2) {
            if (ob instanceof MdlEnteteAppro) {
                MdlEnteteAppro enteteApp = (MdlEnteteAppro) ob;
                preparedStatemant = MdlConnexion.getCnx().prepareCall("Call sp_enteteAppro (?)");
                preparedStatemant.setString(1, enteteApp.getNom());
                int x = preparedStatemant.executeUpdate();
                if (x != 0) {
                    return true;
                }
            } else if (ob instanceof MdlDetailsAppro) {
                MdlDetailsAppro detApp = (MdlDetailsAppro) ob;
                preparedStatemant = MdlConnexion.getCnx().prepareCall("Call sp_approDetail_in (?,?,?)");
                preparedStatemant.setString(1, detApp.getDesiProduit());
                preparedStatemant.setFloat(2, detApp.getQte());
                preparedStatemant.setInt(3, detApp.getId());
                int x = preparedStatemant.executeUpdate();
                if (x != 0) {
                    return true;
                }
            }
        } else if (btn == 1) {
            if (ob instanceof MdlEnteteFacture) {
                MdlEnteteFacture enteteFact = (MdlEnteteFacture) ob;
                preparedStatemant = MdlConnexion.getCnx().prepareCall("Call sp_enteteFacture (?, ?)");
                preparedStatemant.setString(1, enteteFact.getNom());
                preparedStatemant.setInt(2, enteteFact.getType());
                int x = preparedStatemant.executeUpdate();
                if (x != 0) {
                    return true;
                }
            } else if (ob instanceof MdlDetailFacture) {
                MdlDetailFacture detFact = (MdlDetailFacture) ob;
                preparedStatemant = MdlConnexion.getCnx().prepareCall("Call sp_factDetail_in (?,?,?)");
                preparedStatemant.setString(1, detFact.getDesiProduit());
                preparedStatemant.setFloat(2, detFact.getQte());
                preparedStatemant.setInt(3, detFact.getId());
                int x = preparedStatemant.executeUpdate();
                if (x != 0) {
                    return true;
                }
            }
        }

        return false;
    }
}
