/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Optimus
 */
public class ctrl_Fsseur implements Initializable {

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
        // TODO
    }    

    @FXML
    private void btnEnre(ActionEvent event) {
    }
    
}
