/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.CustomPasswordField;
import pack.model.MdlLogin;

/**
 * FXML Controller class
 *
 * @author Optimus
 */
public class ctrl_Login implements Initializable {

    // Inportation objets
    private pack.main.cls_controller ctrl;
    private MdlLogin login;

    @FXML
    private AnchorPane anp_login;
    @FXML
    private TextField txt_nom;
    @FXML
    private CustomPasswordField txt_password;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialisation objets
        ctrl = new pack.main.cls_controller();
        login = new MdlLogin();
    }

    @FXML
    private void connecter(MouseEvent event) throws IOException, ClassNotFoundException, SQLException {

        if (login.tesLogin(txt_nom.getText(), txt_password.getText()) == true) {

            ((Stage) anp_login.getScene().getWindow()).close();
            ctrl._interface("/pack/ui/ui_Principal.fxml", "");

        } else {
            System.out.println("Password incorrect");
        }
    }

    @FXML
    private void onCloseCliked(MouseEvent event) {
        System.exit(0);
    }

}
