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
public class ctrl_RecuComposant implements Initializable {

    @FXML
    private Label idRecu;
    @FXML
    private Label idEnteteFact;
    @FXML
    private Label DatePyt;
    @FXML
    private Label montantPaye;
    
    public static String idRecu_;
    public static String idEnteteFact_;
    public static String DatePyt_;
    public static String montantPaye_;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idRecu.setText(idRecu_);
        idEnteteFact.setText(idEnteteFact_);
        DatePyt.setText(DatePyt_);
        montantPaye.setText(montantPaye_);
    }    
    
}
