/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack.controller;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import pack.main.cls_controller;
import pack.model.MdlPaiement;

/**
 * FXML Controller class
 *
 * @author Optimus
 */
public class ctrl_Paiement implements Initializable {

    @FXML
    private TextField txtNomClient;
    @FXML
    private TextField txtMontant;
    @FXML
    private JFXButton btnEnre;
    @FXML
    private Label txtId;
    @FXML
    private Label lblInfo;
    @FXML
    private FontAwesomeIconView font;
    @FXML
    private Label lblInfo1;
    @FXML
    private FontAwesomeIconView font1;
    
    private cls_controller ctrl;
    private MdlPaiement paie;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ctrl = new cls_controller();
        paie = new MdlPaiement(txtNomClient, txtMontant, lblInfo, font);
    }    

    @FXML
    private void payer(MouseEvent event) throws ClassNotFoundException, SQLException {
        paie.paiementIn(1);
    }
    
}
