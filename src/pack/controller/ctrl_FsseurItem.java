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

/**
 * FXML Controller class
 *
 * @author Optimus
 */
public class ctrl_FsseurItem implements Initializable {

    @FXML
    private Label idFsseur;
    @FXML
    private Label nom;
    @FXML
    private Label addresse;
    @FXML
    private Label contact;
    
    public static String idFsseur_;
    public static String nom_;
    public static String addresse_;
    public static String contact_;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idFsseur.setText(idFsseur_);
        nom.setText(nom_);
        addresse.setText(addresse_);
        contact.setText(contact_);
    }    
    
    
}
