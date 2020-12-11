/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack.conroler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.CustomPasswordField;

/**
 * FXML Controller class
 *
 * @author Optimus
 */
public class ctrl_Login implements Initializable {
    // Inportation objets
    private pack.main.cls_controller ctrl;
            
            
    @FXML
    private CustomPasswordField chpMotDePasse;
    @FXML
    private AnchorPane anp_login;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialisation objets
        ctrl = new pack.main.cls_controller();
    }    

    @FXML
    private void connecter(MouseEvent event) throws IOException {
        ((Stage) anp_login.getScene().getWindow()).close();
         ctrl._interface("/pack/ui/ui_Principal.fxml", "");
    }
    
}
