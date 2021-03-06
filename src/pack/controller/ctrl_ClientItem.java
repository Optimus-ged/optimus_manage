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
import javafx.scene.layout.AnchorPane;
import static pack.controller.ctrl_Client.public_cmbSexe;
import static pack.controller.ctrl_Client.public_txtNom;
import static pack.controller.ctrl_Client.public_txtPhone;

/**
 * FXML Controller class
 *
 * @author Optimus
 */
public class ctrl_ClientItem implements Initializable {

    @FXML
    private Label idClient;
    @FXML
    private Label client;
    @FXML
    private Label sexe;
    @FXML
    private Label contact;

    public static String idClient_;
    public static String client_;
    public static String sexe_;
    public static String contact_;
    @FXML
    private AnchorPane cardList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idClient.setText(idClient_);
        client.setText(client_);
        sexe.setText(sexe_);
        contact.setText(contact_);
        isEvent();
    }

    void isEvent() {
        cardList.setOnMouseClicked((Action) -> {
            public_txtNom.setText(client.getText());
            public_txtPhone.setText(contact.getText());
            public_cmbSexe.setValue(sexe.getText());
        });
    }
}
