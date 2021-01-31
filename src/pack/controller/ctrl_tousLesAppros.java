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
public class ctrl_tousLesAppros implements Initializable {

    @FXML
    private Label idFsseur;
    @FXML
    private Label nomFsseur;
    @FXML
    private Label contact;
    @FXML
    private Label addresseFsseur;

    /**
     * Initializes the controller class.
     */
    public static String idFsseur_;
    public static String nomFsseur_;
    public static String contactA_;
    public static String addresseFsseir_;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idFsseur.setText(idFsseur_);
        nomFsseur.setText(nomFsseur_);
        contact.setText(contactA_);
        addresseFsseur.setText(addresseFsseir_);
    }    
    
}
