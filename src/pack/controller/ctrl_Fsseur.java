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
import pack.model.MdlClient;
import pack.model.MdlFsseur;

/**
 * FXML Controller class
 *
 * @author Optimus
 */
public class ctrl_Fsseur implements Initializable {
     private cls_controller ctrl;
    private MdlFsseur fss;
    
    @FXML
    private Label txtId;
    @FXML
    private TextField txtNom;
    @FXML
    private TextField txtAddresse;
    @FXML
    private TextField txtPhone;
    @FXML
    private Label lblInfo;
    @FXML
    private FontAwesomeIconView font;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ctrl = new cls_controller();
        fss = new MdlFsseur(txtId, lblInfo, txtNom, txtAddresse, txtPhone, font);
    }    

    @FXML
    private void btnEnre(ActionEvent event) throws ClassNotFoundException, SQLException {
        fss.fsseurIn(1);
    }
    
}
