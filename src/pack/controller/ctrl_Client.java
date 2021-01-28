/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack.controller;

import com.jfoenix.controls.JFXListView;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import pack.main.cls_controller;
import pack.model.MdlClient;
import static pack.controller.ctrl_ClientItem.client_;
import static pack.controller.ctrl_ClientItem.idClient_;
import static pack.controller.ctrl_ClientItem.sexe_;
import static pack.controller.ctrl_ClientItem.contact_;
import pack.model.MdlConnexion;


/**
 * FXML Controller class
 *
 * @author Optimus
 */
public class ctrl_Client implements Initializable {
    private cls_controller ctrl;
    private MdlClient cli;

    @FXML
    private TextField txtNom;
    @FXML
    private TextField txtPrenom;
    @FXML
    private ComboBox<String> cmbSexe;
    @FXML
    private Label txtId;
    @FXML
    private Label lblInfo;
    @FXML
    private FontAwesomeIconView font;
    @FXML
    private TextField txtTelephone;
    private ResultSet res;
    @FXML
    private JFXListView<?> lstview_client;
    @FXML
    private TextField txtRecherche;
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ctrl = new cls_controller();
        cli = new MdlClient(txtNom, txtPrenom, txtPrenom, cmbSexe, txtTelephone, txtId, lblInfo, font);
        ctrl.chargeCmbSexe(cmbSexe);
        initList(
                lstview_client,
                "SELECT id, nom, sexe, telephone FROM client",
                "/pack/composants/ui_ClientItem.fxml"
        );
    }    

    @FXML
    private void enregClient(ActionEvent event) throws ClassNotFoundException, SQLException {
        cli.clientIn(1);
         initList(
              lstview_client,
              "SELECT id, nom, sexe, telephone FROM client",
              "/pack/composants/ui_ClientItem.fxml"
        );
    }
    
    private void initList(JFXListView list, String requette, String uiFx){
        try {
            list.getItems().clear();
            res = MdlConnexion.getCnx().createStatement().executeQuery(requette);
            while(res.next()){
                idClient_= res.getString("id");
                client_ = res.getString("nom");
                sexe_= res.getString("sexe");
                contact_= res.getString("telephone");
                list.getItems().add(FXMLLoader.load(getClass().getResource(uiFx)));
            }
        } catch (Exception e) {
        }
    }

    @FXML
    private void selectClient(KeyEvent event) {
         initList(
              lstview_client,
              "SELECT id, nom, sexe, telephone FROM client WHERE nom LIKE '%"+ txtRecherche.getText() +"%'",
              "/pack/composants/ui_ClientItem.fxml"
        );
    }
    
}
