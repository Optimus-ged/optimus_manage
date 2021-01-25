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
public class ctrl_produitDetail implements Initializable {

    @FXML
    private Label txtPu;
    @FXML
    private Label txtDesignation;
    @FXML
    private Label txtQte;
    
    public static String txtPu_;
    public static String txtDesignation_;
    public static String txtQte_;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtPu.setText(txtPu_);
        txtDesignation.setText(txtDesignation_);
        txtQte.setText(txtQte_);
    }    
    
}
