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
public class ctrl_AgentItem implements Initializable {

    @FXML
    private Label idAgent;
    @FXML
    private Label agent;
    @FXML
    private Label sexe;
    @FXML
    private Label poste;
    @FXML
    private Label contact;

    /**
     * Initializes the controller class.
     */
    
    public static String idAgent_;
    public static String agent_;
    public static String sexe_;
    public static String poste_;
    public static String contact_;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        idAgent.setText(idAgent_);
        agent.setText(agent_);
        sexe.setText(sexe_);
        poste.setText(poste_);
        contact.setText(contact_);
    }   
    
    
    
}
