/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack.controller;

import com.jfoenix.controls.JFXListView;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pack.main.cls_controller;
import pack.model.MdlConnexion;
import pack.model.MdlPaiement;
import static pack.controller.ctrl_RecuComposant.DatePyt_;
import static pack.controller.ctrl_RecuComposant.idEnteteFact_;
import static pack.controller.ctrl_RecuComposant.idRecu_;
import static pack.controller.ctrl_RecuComposant.montantPaye_;

/**
 * FXML Controller class
 *
 * @author Optimus
 */
public class ctrl_Paiement implements Initializable {
    private ResultSet res;
    
    @FXML
    private TextField txtMontant;
    
    private cls_controller ctrl;
    private MdlPaiement paie;
    
    @FXML
    private TextField txtidFacture;
    @FXML
    private JFXListView<?> listeRecu;
    @FXML
    private Label lblInfo;
    @FXML
    private FontAwesomeIconView font;
    private Label label_test;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ctrl = new cls_controller();
        paie = new MdlPaiement(txtidFacture, txtMontant, lblInfo, font, label_test);
        initList(
              listeRecu,
              "SELECT * FROM `paiement`",
              "/pack/composants/ui_RecuComposant.fxml"
        );
    }    

    
    @FXML
    private void fairePaiement(ActionEvent event) throws ClassNotFoundException, SQLException {
        paie.paiementIn(1);
        initList(
              listeRecu,
              "SELECT * FROM `paiement`",
              "/pack/composants/ui_RecuComposant.fxml"
        );
    }
    
     private void initList(JFXListView list, String requette, String uiFx){
        try {
            list.getItems().clear();
            res = MdlConnexion.getCnx().createStatement().executeQuery(requette);
            while(res.next()){
                idRecu_ = res.getString("id");
                idEnteteFact_ = res.getString("idEnteteFacture");
                DatePyt_ = res.getString("datepaiement") ;
                montantPaye_ = res.getString("montantPaye");
                list.getItems().add(FXMLLoader.load(getClass().getResource(uiFx)));
            }
        } catch (Exception e) {
        }
}
    
}
