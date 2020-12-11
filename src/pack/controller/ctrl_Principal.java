/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Optimus
 */
public class ctrl_Principal implements Initializable {
    // Inportation objets
    private pack.main.cls_controller ctrl;
    
    @FXML
    private StackPane principalContainer;
    @FXML
    private AnchorPane anp_principal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         // Initialisation objets
        ctrl = new pack.main.cls_controller();
    }    

    @FXML
    private void deconnection(ActionEvent event) throws IOException {
        ((Stage) anp_principal.getScene().getWindow()).close();
        ctrl._interfaceNoBoarder("/pack/ui/ui_Login.fxml");
    }

    @FXML
    private void onAccueilCliked(MouseEvent event) throws IOException {
        ctrl._interface(principalContainer, "/pack/ui/ui_Accueil.fxml");
    }

    @FXML
    private void onComptabiliteCliked(MouseEvent event) throws IOException {
        ctrl._interface(principalContainer, "/pack/ui/ui_Comptabilite.fxml");
    }

    @FXML
    private void onRapportsClicked(MouseEvent event) throws IOException {
        ctrl._interface(principalContainer, "/pack/ui/ui_Rapports.fxml");
    }

    @FXML
    private void onParametresClicked(MouseEvent event) throws IOException {
        ctrl._interface(principalContainer, "/pack/ui/ui_Parametres.fxml");
    }
    
}
