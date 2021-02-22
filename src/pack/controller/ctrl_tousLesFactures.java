/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Optimus
 */
public class ctrl_tousLesFactures implements Initializable {

    @FXML
    private Label idFacture;
    @FXML
    private Label nomClient;
    @FXML
    private Label sexe;
    @FXML
    private Label contact;
    
    
    public static String idFacture_;
    public static String nomClient_;
    public static String sexe_;
    public static String contact_;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idFacture.setText(idFacture_);
        nomClient.setText(nomClient_);
        sexe.setText(sexe_);
        contact.setText(contact_);
    }    

    @FXML
    private void click_item(MouseEvent event) {
    }
    
}
