/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pack.main.cls_controller;
import pack.model.MdlPaiement;

/**
 * FXML Controller class
 *
 * @author Optimus
 */
public class ctrl_Paiement implements Initializable {

    
    @FXML
    private TextField txtMontant;
    @FXML
    private Label lblInfo;
    @FXML
    private FontAwesomeIconView font;
    
    private cls_controller ctrl;
    private MdlPaiement paie;
    
    @FXML
    private TextField txtidFacture;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ctrl = new cls_controller();
        paie = new MdlPaiement(txtidFacture, txtMontant, lblInfo, font);
    }    

    
    @FXML
    private void fairePaiement(ActionEvent event) throws ClassNotFoundException, SQLException {
        paie.paiementIn(1);
    }
    
}
