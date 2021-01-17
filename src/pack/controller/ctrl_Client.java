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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pack.main.cls_controller;
import pack.model.MdlClient;

/**
 * FXML Controller class
 *
 * @author Optimus
 */
public class ctrl_Client implements Initializable {
    private cls_controller ctrl;
    private MdlClient cli;

    @FXML
    private TextField txtNom;
    @FXML
    private TextField txtPrenom;
    @FXML
    private ComboBox<String> cmbSexe;
    private TextField txtPoste;
    @FXML
    private Label txtId;
    @FXML
    private Label lblInfo;
    @FXML
    private FontAwesomeIconView font;
    @FXML
    private TextField txtTelephone;

   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ctrl = new cls_controller();
        cli = new MdlClient(txtNom, txtPrenom, txtPrenom, cmbSexe, txtPoste, txtId, lblInfo, font);
        ctrl.chargeCmbSexe(cmbSexe);
    }    

    @FXML
    private void enregClient(ActionEvent event) throws ClassNotFoundException, SQLException {
        cli.clientIn(1);
    }
    
}
