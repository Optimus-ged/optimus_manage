/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack.controller;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void payer(MouseEvent event) {
    }
    
}
