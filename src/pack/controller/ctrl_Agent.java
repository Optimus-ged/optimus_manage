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
import pack.model.MdlAgent;
import static pack.controller.ctrl_AgentItem.agent_;
import static pack.controller.ctrl_AgentItem.contact_;
import static pack.controller.ctrl_AgentItem.idAgent_;
import static pack.controller.ctrl_AgentItem.sexe_;
import static pack.controller.ctrl_AgentItem.poste_;
import pack.model.MdlConnexion;

/**
 * FXML Controller class
 *
 * @author Optimus
 */
public class ctrl_Agent implements Initializable {
    private cls_controller ctrl;
    private MdlAgent ag;

    @FXML
    private TextField txtNom;
    @FXML
    private TextField txtPrenom;
    @FXML
    private ComboBox<String> cmbSexe;
    @FXML
    private TextField txtPoste;
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
    private JFXListView<?> lstview_agents;
    @FXML
    private TextField rechercheAgent;
   


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ctrl = new cls_controller();
        ag = new MdlAgent(txtNom, txtPrenom, txtTelephone, cmbSexe, txtPoste, txtId, lblInfo, font);
        ctrl.chargeCmbSexe(cmbSexe);
        initList(
              lstview_agents,
              "SELECT id, nom, sexe, poste, telephone FROM agent",
              "/pack/composants/ui_AgentItem.fxml"
        );
    }    

    @FXML
    private void enregAgent(ActionEvent event) throws ClassNotFoundException, SQLException {
        ag.agentIn(1);
        initList(
              lstview_agents,
              "SELECT id, nom, sexe, poste, telephone FROM agent",
              "/pack/composants/ui_AgentItem.fxml"
        );
    }

    private void initList(JFXListView list, String requette, String uiFx){
        try {
            list.getItems().clear();
            res = MdlConnexion.getCnx().createStatement().executeQuery(requette);
            while(res.next()){
                idAgent_= res.getString("id");
                agent_ = res.getString("nom");
                sexe_= res.getString("sexe");
                contact_= res.getString("telephone");
                poste_= res.getString("poste");
                list.getItems().add(FXMLLoader.load(getClass().getResource(uiFx)));
            }
        } catch (Exception e) {
        }
    }

    @FXML
    private void selectAgent(KeyEvent event) {
        initList(
              lstview_agents,
              "SELECT id, nom, sexe, poste, telephone FROM agent WHERE nom LIKE '%"+ rechercheAgent.getText() +"%'",
              "/pack/composants/ui_AgentItem.fxml"
        );
    }
}
