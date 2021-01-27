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
public class ctrl_ProduitItem implements Initializable {

    @FXML
    private Label idProduit;
    @FXML
    private Label designation;
    @FXML
    private Label PU;
    
    public static String idProduit_;
    public static String designation_;
    public static String pu_;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idProduit.setText(idProduit_);
        designation.setText(designation_);
        PU.setText(pu_);
    }    
    
}
