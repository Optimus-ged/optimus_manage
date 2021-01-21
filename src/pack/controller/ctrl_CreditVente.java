/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Optimus
 */
public class ctrl_CreditVente implements Initializable {

    @FXML
    private TextField txtTelephoneClient;
    @FXML
    private TextField txtQteProduit;
    @FXML
    private Label txtId;
    @FXML
    private Label lblInfo;
    @FXML
    private FontAwesomeIconView font;
    @FXML
    private TextField txtPuProduit;
    @FXML
    private VBox vboxDetail;
    @FXML
    private Label idFacture;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
